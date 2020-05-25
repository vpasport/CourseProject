<script context="module">
    import Fetcher from "/helpers/fetcher.js";

    export async function preload(page, session){
        const fetcher = new Fetcher(this.fetch);
        const id = page.query.id;
        const executor = (await fetcher.get(`http://25.90.180.175:8080/users/getByIdJson?id=${id}`));

        console.log(executor)

        return { executor, id };
    }
</script>

<script>
    export let executor, id;

    const fetcher = new Fetcher();

    console.log(executor)
</script>

<style lang="scss">
    @import "./styles/global.scss";

    .container{
        @extend %container;
        margin-top: 30px;
    }

    .executor-header{
        @include base-flex(space-between, center);

        &__name{
            font-size: $extra-large;
        }

        &__send_action-button{
            @extend %action-button;
        }
    }

    .executor{
        @extend %back-shadow;
        padding: 20px;
        margin-top: 20px;

        & > *:not(:first-child){
            margin-top: 15px;
        }

        &__about-text{
            font-size: $large;
            font-weight: bold;
        }

        &__data{

            & > li{
                font-size: $medium;

                &:not(:first-child){
                    margin-top: 10px;
                }
            }
        }
    }
</style>

<div class="container">
    <div class="executor-header">
        <h1 class="executor-header__name">{executor.firstName} {executor.secondName}</h1>
        <button class="executor-header__send_action-button">Предложить работу</button>
    </div>
    <div class="executor">
        <h2 class="executor__about-text">О пользователе</h2>
        <ul class="executor__data">
            <li class="eecutor__data__speciality">Специальность: {executor.speciality}</li>
            <li class="executor__data__email">Почта: {executor.email}</li>
            <li class="executor__data__phone">Телефон: {executor.phone}</li>
            <li class="executor__data__age">Возраст: {executor.age}</li>
            <li class="executor__data__sex">Пол: {executor.sex === "Male" ? "Мужчина" : "Женщина"}</li>
            <li class="executor__data__raiting">Рейтинг: {executor.rating}</li>
        </ul>
    </div>
</div>