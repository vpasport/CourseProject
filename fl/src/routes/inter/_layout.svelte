<script>

</script>

<style lang="scss">
    @import "./styles/inter";

    .container{
        @extend %container;
        @extend %flex-align-center;
        height: calc(100vh - 75px);
        flex-direction: column;
        justify-content: center;
    }
</style>

<div class="container">
    <form class="form">
        <slot/>
    </form>
</div>