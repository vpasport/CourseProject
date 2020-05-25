<script>
    export let executor;
</script>

<style lang="scss">
    @import "./styles/global";

    .executor{
        @extend %back-shadow;
        padding: 20px;

        &__header{
            @include base-flex(space-between, flex-start);

            &__name{
                font-size: $large;
            }
        }

        &__line{
            border-top: 1px solid $back2;
            width: calc(100% + 40px);
            margin: 20px 0 20px -20px;
        }

        &__footer{
            @include base-flex(space-between, center);

            &__speciality{
                font-size: $small;
            }

            &__more-detail_action-button{
                @extend %action-button;
            }
        }
    }
</style>

<section class="executor">
    <div class="executor__header">
        <h3 class="executor__header__name">{executor.firstName} {executor.secondName}</h3>
    </div>
    <hr class="executor__line">
    <div class="executor__footer">
        <span class="executor__footer__speciality">{executor.specialty}</span>
        <a href={`/executor?id=${executor.id}`} class="executor__footer__more-detail_action-button">Подробее</a>
    </div>
</section>