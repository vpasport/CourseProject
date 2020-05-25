<script>
  import Header from "/components/header.svelte";
</script>

<style lang="scss">
  @import "./styles/global";

  :global(*) {
    font-family: $main-font !important;
  }
</style>

<Header />
<slot />
