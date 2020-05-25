<script>
  let isLogin = false;
</script>

<style lang="scss">
  @import "./styles/global.scss";

  .header {
    @extend %back-shadow;
    padding: 20px;

    &__container{
        @extend %flex-align-center;
        @extend %container;
        justify-content: space-between;
    }

    &__name {
      @include logo($large);
      text-decoration: none;

      &__logo{
        margin-right: 10px;
        width: 35px;
      }
    }

    &__right {
      &__user-info {
        @extend %flex-align-center;

        &__user {
          color: black;
          text-decoration: none;
          font-weight: bold;
          font-size: $medium;
          margin-right: 30px;
        }

        &__logout_round-button_dark-back {
          @extend %header-link;
        }
      }

      &__register-buttons {
        @extend %flex-align-center;

        &__register_round-button_dark-back {
          margin-right: 30px;
        }

        &__register_round-button_dark-back,
        &__login_round-button_dark-back {
          @extend %header-link;
        }
      }
    }
  }

  %header-link {
    @include round-button(bold);
    background: $main-color2;
    text-decoration: none;
  }
</style>

<header class="header">
  <div class="header__container">
    <a href="/" class="header__name"> <img src="/img/logo.png" alt="logo" class="header__name__logo"> Биржа фриланса</a>
    <div class="header__right">
      {#if isLogin}
        <div class="header__right__user-info">
          <a href="./user/orders" class="header__right__user-info__user">
            *Имя Фамилия*
          </a>
          <a
            href="/logout"
            class="header__right__user-info__logout_round-button_dark-back">
            Выход
          </a>
        </div>
      {/if}
      <div class="header__right__register-buttons">
        <a
          href="inter/register"
          class="header__right__register-buttons__register_round-button_dark-back">
          Регистрация
        </a>
        <a
          href="inter/login"
          class="header__right__register-buttons__login_round-button_dark-back">
          Вход
        </a>
      </div>
    </div>
  </div>
</header>
