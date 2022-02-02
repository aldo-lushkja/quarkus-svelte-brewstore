<script>
	let search;
	let isLoading = false;
	let onError = false;
	let beers = [];
	const BEER_API = "http://localhost:12312/beers/search?page=1&per_page=80";
	const getBeers = async () => {
		const response = await fetch(BEER_API);

		if (response.status !== 200) {
			isLoading = false;
			onError = true;
			console.log(
				"Error calling API, response with status " + response.status
			);
			return;
		}
		const json = await response.json();
		beers = json;
		console.log(beers);
	};

	const handleSubmit = (e) => {
		e.preventDefault();
		isLoading = true;
		getBeers();
		isLoading = false;
	};
</script>

<div class="container is-fluid block box">
	<form on:submit={handleSubmit}>
		<h1 class="is-size-3">Beers API 🍺🥃🍻</h1>
		<input
			type="text"
			name="search"
			bind:value={search}
			placeholder="Search a beer..."
		/>
		<button type="submit" class="button is-primary is-rounded"
			>Search</button
		>
	</form>
</div>

<div class="container is-fluid columns is-variable is-multiline">
	{#each beers as beer}
		<div class="column is-one-quarter">
			<div class="card ">
				<div class="card-image">
					<figure class="image is-square">
						<img src={beer.image_url} alt="Placeholder image" />
					</figure>
				</div>
				<div class="card-content">
					<div class="media">
						<div class="media-content">
							<p class="title is-4">{beer.name}</p>
							<p class="subtitle is-6">{beer.description}</p>
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
