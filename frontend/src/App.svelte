
<script>
    import {onMount, onDestroy} from "svelte";
    import {writable, derived} from "svelte/store";
    import {_, locale} from "svelte-i18n";

    import { API_URL } from "../config";

    let search = '';
    let isLoading = writable(false);
    let onError = writable(false);
    let beers = writable([]);
    // let tags = [];
    let executionTimeFull;
    let executionTime;
    let debounceTimer;

    const formatter = new Intl.NumberFormat('en-US', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2,
    });

    const beersList = derived(beers, $beers => {
        return Array.isArray($beers) ? $beers : [];
    });

    const searchBrewery = (searchTerm) => {
        // Clear any existing timer
        if (debounceTimer) clearTimeout(debounceTimer);

        // Only search if term is long enough
        if (searchTerm.length < 3) {
            beers.set([]);
            return;
        }

        isLoading.set(true);
        const API = `${API_URL}/breweries/search`;

        console.log('Fetching from url: ' + API);
        // Set a new debounce timer
        debounceTimer = setTimeout(() => {
            onError.set(false);
            console.log('Fetching from url: ' + API);
            console.log('Using search text: ' + searchTerm);

            const startTime = performance.now();

            const payload = {
                name: searchTerm,
                page: 1,
                per_page: 10,
            };
            fetch(API, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(payload),
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    executionTimeFull = (performance.now() - startTime) / 1000;
                    executionTime = formatter.format(executionTimeFull);

                    beers.set(data);
                    // tags = data.map(beer => beer.tagline);
                    // console.log(new Set(tags));
                    isLoading.set(false);
                })
                .catch(error => {
                    console.error("Error fetching beers:", error);
                    isLoading.set(false);
                    onError.set(true);
                })
                .finally(() => {
                    isLoading.set(false);
                });
        }, 1000); // 500ms delay
    };

    // Cleanup subscription on component destroy
    onDestroy(() => {
        if (debounceTimer) clearTimeout(debounceTimer);
    });

    const getCountryEmoji = (country) => {
        const codePoints = country
            .toUpperCase()
            .split('')
            .map(char => 127397 + char.charCodeAt(0));
        return String.fromCodePoint(...codePoints);
    };


    const handleClick = () => {
        searchBrewery(search);
    };
</script>

<main>
    <div class="container is-fluid has-text-centered">
        <h1 class="title is-2 is-centered">Brewery Hall</h1>
        <div class="field has-addons is-centered" style="width: 300px; margin: 0 auto;">
            <div class="control is-expanded has-addons-centered">
                <input
                        type="text"
                        name="search"
                        bind:value={search}
                        class="input is-primary  is-small"
                        placeholder="Cheer me up!"
                />
            </div>
            <div class="control">
                <button on:click={handleClick} class="button is-primary is-small">
                    Search
                </button>
            </div>
        </div>
    </div>

    {#if !$isLoading && !$onError}
        <div class="container is-fluid columns is-variable is-multiline">
            {#if executionTime}
                <div class="column is-full">
                    <h2 class="sub-title is-size-6">{$beersList.length} results in {executionTime} seconds</h2>
                </div>
            {/if}

            {#each $beersList as beer (beer.id)}
                <div class="column is-one-quarter">
                    <div class="card">
                        <div class="card-content">
                            <div class="media">
                                <div class="media-content has-text-centered">
                                    <p class="title is-4">{beer.name}</p>
                                    <p class="subtitle is-6">{beer.breweryType}</p>
                                    <p>{beer.address1}</p>
                                    <p>{beer.address2}</p>
                                    <p>{beer.address3}</p>
                                    <p>{beer.city}, {beer.stateProvince} {beer.postalCode}</p>
                                    <p class="sub-title">{beer.country} {getCountryEmoji(beer.country)}</p>
                                    <p>Longitude: {beer.longitude}</p>
                                    <p>Latitude: {beer.latitude}</p>
                                    <p>Phone: {beer.phone}</p>
                                    <p><a href={beer.websiteUrl} target="_blank">{beer.websiteUrl}</a></p>
                                    <p>State: {beer.state}</p>
                                    <p>Street: {beer.street}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            {/each}
        </div>
    {/if}
    {#if $onError}
        <div class="container is-fluid has-text-centered">
            <img
                    class="is-128x128 is-centered mt-2"
                    alt="error occurred"
                    src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2F2.bp.blogspot.com%2F-CPO_z4zNSnc%2FWsY667p0JgI%2FAAAAAAAAYRs%2FubTMJD5ToyImbR-o4EiK18gBypYXd0RiwCLcBGAs%2Fs1600%2FMercenary%252BGarage%252BError%252BGIF.gif&f=1&nofb=1"
                    width="300"
                    height="80"
            />
        </div>
    {/if}

    {#if $isLoading}
        <div id="loading" class="is-centered">
            <img
                    alt="is loading"
                    src="https://cdn.dribbble.com/users/278870/screenshots/1573076/preloader_gif.gif"
                    width="150"
            />
        </div>
    {/if}

</main>


<style>
    @import url("https://fonts.googleapis.com/css?family=Nunito:400,700");

    main {
        font-family: "Nunito", sans-serif;
    }

    #loading {
        display: flex;
        justify-content: center;
        align-items: center;
        vertical-align: middle;
    }


</style>