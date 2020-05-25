<script context="module">
  import Fetcher from "/helpers/fetcher.js";

  export async function preload(page, session){
    const fetcher = new Fetcher(this.fetch);

    const orders = await fetcher.get(`http://25.90.180.175:8080/orders/allJson`, {
      credentials: "same-origin"
    })

    const executors = await fetcher.get(`http://25.90.180.175:8080/users/getallexecutorsJson`, {
      credentials: "same-origin"
    })
    
    return { orders, executors };
  }
</script>

<script>	
  import { onMount } from "svelte";
  import Order from "/components/order.svelte";
  import Executor from "/components/executor.svelte";

  export let orders;
  export let executors;

  console.log(executors)

  const fetcher = new Fetcher();

  let isExecutor = false;
  let list;

  $: list = isExecutor ? orders : executors;
</script>

<style lang="scss">
  @import "./styles/global";

  .container{
    @extend %container;
  }

  .page-name{
    @include base-flex(space-between, center);
    margin-top: 20px;

    &__page-text{
      font-size: $extra-large;
    }
  }

  .action-button{
    @extend %action-button; 
  }

  .executor-change_action-button{
    @extend .action-button;
    position: fixed;
    right: 10px;
    bottom: 10px;
  }

  .main-list{
      display: grid;
      grid-template-columns: repeat(3, 350px);
      justify-content: space-between;
      grid-row-gap: 40px;
      margin-top: 30px;

      &__item{
          width: 350px;
      }
  }
</style>

<svelte:head>
	<title>Биржа фриланса</title>
</svelte:head>

<div class="container">
  <div class="page-name">
    <h1 class="page-name__text">
      {#if !isExecutor}
        Каталог заказчиков
      {:else}
        Каталог заказов
      {/if}
    </h1>
    {#if !isExecutor}
      <a href="/new-order" class="action-button">Разместить заказ</a>
    {/if}
  </div>

  <ul class="main-list">
    {#each list as item}
      <li class="main-list__item">
        {#if isExecutor}
          <Order order={item} />
        {:else}
          <Executor executor={item} />
        {/if}
      </li>
    {/each}
  </ul>
</div>

<button class="executor-change_action-button" on:click={() => isExecutor = !isExecutor}>
  {#if !isExecutor}
    Я заказчик
  {:else}
    Я исполнитель
  {/if}
</button>