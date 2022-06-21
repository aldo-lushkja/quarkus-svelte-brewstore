<script>
	let search;
	let isLoading = false;
	let onError = false;
	let beers = [];
	let executionTime;

	const getBeers = async () => {
		const BEER_API = `http://brewstore-backend:8089/beers/search-by-name/${search}/?page=1&per_page=80`;
		console.log(BEER_API)
		const startTime = performance.now();

		const response = await fetch(BEER_API);
		console.log('Searching for ' + search)

		executionTime =  (performance.now() - startTime) / 1000
		if (response.status !== 200) {
			isLoading = false;
			onError = true;
			console.log(
				"Error calling API, response with status " + response.status
			);
			return;
		}
		beers = await response.json();
		console.debug(beers);
	};

	const handleClick = (e) => {
		isLoading = true;
		getBeers();
		isLoading = false;
	};
</script>

<div class="container is-fluid is-flex box">
		<h1 class="is-size-3 ml-2">🍺🥃🍻 BrewStore 🍺🥃🍻</h1>
	<div class="control">
		<input
				type="text"
				name="search"
				bind:value={search}
				class="input is-primary is-rounded is-medium is-loading ml-2"
				style="width: 20rem"
				placeholder="Search a beer..."
		/>
	</div>
		<button on:click={handleClick} class="button is-primary is-rounded ml-2 mt-1"
			>Cheers</button
		>
</div>

<div class="container is-fluid columns is-variable is-multiline">
	{#if executionTime}<div class="column is-full"><h2 class="is-size-6 ">Circa {beers.length} risultati  ({executionTime} secondi)</h2></div>{/if}

	{#each beers as beer}
		<div class="column is-one-quarter">
			<div class="card ">
				<div class="card-image">
					<figure class="image is-square ">
						<img src={beer.image_url} alt="Placeholder image" />
					</figure>
				</div>
				<div class="card-content">
					<div class="media">
						<div class="media-content">
							<p class="title is-4">{beer.name}</p>
							<p class="subtitle is-6">{#if beer.description.length > 200} {beer.description.substring(200)}... {:else} {beer.description}  {/if}</p>
						</div>
					</div>

					<div class="content" />
				</div>
			</div>
		</div>
	{/each}
</div>

{#if isLoading}
	<img
		alt="is loading"
		src="https://cdn.dribbble.com/users/278870/screenshots/1573076/preloader_gif.gif"
		width="100"
	/>
{/if}

{#if onError}
	<div class="container is-fluid">
		<img
			class="image is-128x128"
			alt="error occured"
			src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2F2.bp.blogspot.com%2F-CPO_z4zNSnc%2FWsY667p0JgI%2FAAAAAAAAYRs%2FubTMJD5ToyImbR-o4EiK18gBypYXd0RiwCLcBGAs%2Fs1600%2FMercenary%252BGarage%252BError%252BGIF.gif&f=1&nofb=1"
			width="300"
		/>
	</div>
{/if}

<style>

</style>
