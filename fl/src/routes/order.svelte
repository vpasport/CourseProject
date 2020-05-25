<script context="module">
  import Fetcher from "/helpers/fetcher.js";

  export async function preload(page, session) {
    const fetcher = new Fetcher(this.fetch);
    const id = page.query.id;
    const order = await fetcher.get(
      `http://25.90.180.175:8080//orders/getByIdJson?id=${id}`
    );

    return { id, order };
  }
</script>

<script>
  export let id, order;

  const fetcher = new Fetcher();

  console.log(order);
</script>

<style lang="scss">
  @import "./styles/global";

  .container {
    @extend %container;
    margin-top: 30px;
  }

  .order-header {
    @include base-flex(space-between, center);

    &__name {
      font-size: $extra-large;
    }

    &__send-message_action-button {
      @extend %action-button;
    }
  }

  .order {
    @extend %back-shadow;
    margin-top: 20px;

    &__main {
      display: flex;

      &__info {
        flex: 0.65;
        padding: 20px;
        border-right: 1px solid $back2;

        & > *:not(:first-child) {
          margin-top: 20px;
        }

        &__section-name {
          font-size: $large;
        }

        &__text {
          white-space: pre-wrap;
        }

        &__low-data {
          @include base-flex(space-between, center);

          &__deadline{
              font-weight: bold;
          }
        }

        &__price-section {
          @extend %flex-align-center;

          &__price {
            display: block;
            margin-left: 15px;
            font-size: $large;
            color: $main-color;
          }
        }
      }

      &__customer {
        flex: 0.35;
        padding: 20px;

        & > *:not(:first-child) {
          margin-top: 20px;
        }

        &__section-name,
        &__name {
          font-size: $large;
        }

        &__data > li:not(:first-child) {
          margin-top: 10px;
        }
      }
    }

    &__low-data{
        @include base-flex(space-between, center);
        border-top: 1px solid $back2;
        padding: 20px;

        &__executors-length, &__post-date{
            font-size: small;
        }
    }
  }
</style>

<div class="container">
  <div class="order-header">
    <h1 class="order-header__name">{order.name}</h1>
    <button class="order-header__send-message_action-button">
      Предложить кандидатуру
    </button>
  </div>
  <div class="order">
    <div class="order__main">
      <section class="order__main__info">
        <h2 class="order__main__info__section-name">О заказе</h2>
        <pre class="order__main__info__text">{order.description}</pre>
        <div class="order__main__info__low-data">
          <span class="order__main__info__low-data__category">
            Категория: {order.category.name}
          </span>
          <time class="order__main__info__low-data__deadline">
            Дедлайн: {order.deadline.dayOfMonth} {order.deadline.month}
          </time>
        </div>
        <div class="order__main__info__price-section">
          Цена:
          <span class="order__main__info__price-section__price">
            {order.maxPrice}
          </span>
        </div>
      </section>
      <section class="order__main__customer">
        <h2 class="order__main__customer__section-name">О заказчике</h2>
        <span class="order__main__customer__name">
          {order.customer.firstName} {order.customer.secondName}
        </span>
        <ul class="order__main__customer__data">
          <li class="order__main__customer__data__phone-section">
            Телефон: {order.customer.phone}
          </li>
          <li class="order__main__customer__data__email">
            Email: {order.customer.email}
          </li>
          <li class="order__main__customer__data__sex">
            Пол: {order.customer.sex === 'Male' ? 'Мужской' : 'Женский'}
          </li>
          <li class="order__main__customer__data__age">
            Возраст: {order.customer.age}
          </li>
        </ul>
      </section>
    </div>
    <div class="order__low-data">
      <span class="order__low-data__executors-length">
        Готовы взяться за работу: {order.executors ? order.executors.length : 0}
        человек
      </span>
      <time class="order__low-data__post-date">
        Время публикации: {order.date.dayOfMonth} {order.date.month}
      </time>
    </div>
  </div>
</div>
