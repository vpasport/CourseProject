<script>
  import Fetcher from "/helpers/fetcher.js";

  const fetcher = new Fetcher();

  let login = "";
  let password = "";

  async function auth() {
    let data = await fetch(`http://25.90.180.175:8080/users/auth?login=${login}&password=${password}`)

    window.location.href = "./";
  }
</script>

<style lang="scss">
  @import "./styles/inter.scss";
</style>

<h1>Вход</h1>
<div class="input-area">
  <label for="login" class="input-area__label">Логин</label>
  <br />
  <input
    type="text"
    class="input-area__text-input"
    name="login"
    bind:value={login} />
</div>
<div class="input-area">
  <label for="password" class="input-area__label">Пароль</label>
  <br />
  <input
    type="password"
    class="input-area__text-input"
    name="password"
    bind:value={password} />
</div>
<button class="form__send_action-button" type="button" on:click={auth}>
  Войти
</button>
