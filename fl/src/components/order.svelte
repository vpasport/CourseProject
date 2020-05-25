<script>
  export let order;
</script>

<style lang="scss">
  @import "./styles/global.scss";

  .order {
    @extend %back-shadow;
    width: 100%;
    padding: 20px;

    &__header {
      @include base-flex(space-between, flex-start);

      &__name, &__deadline {
        font-size: $large;
      }
    }

    &__line {
      width: calc(100% + 40px);
      margin: 20px 0 20px -20px;
      border-top: 1px solid $back2;
    }

    &__description {
      white-space: pre-wrap;
    }

    &__footer {
      @include base-flex(space-between, center) &__category {
        font-size: $small;
      }

      &__more-details_action-button {
        @extend %action-button;
      }
    }
  }
</style>

<section class="order">
  <div class="order__header">
    <h2 class="order__header__name">{order.name}</h2>
    <time class="order__header__deadline">До {order.deadline.dayOfMonth} {order.deadline.month}</time>
  </div>
  <hr class="order__line" />
  <pre class="order__description">{order.description}</pre>
  <hr class="order__line" />
  <div class="order__footer">
    <span class="order__footer__category">{order.category.name}</span>
    <a
      href={`/order?id=${order.id}`}
      class="order__footer__more-details_action-button">
      Подробнее
    </a>
  </div>
</section>
