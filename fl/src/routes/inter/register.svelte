<script>
  import Fetcher from "/helpers/fetcher.js";
  import { onMount } from "svelte";

  const fetcher = new Fetcher();

  let login = "";
  let password = "";
  let phone = "";
  let name = "";
  let surname = "";
  let email = "";
  let specialty = "";
  let age = "";
  let sex = true;
  let role = true;

  async function register(){
    let data = await fetcher.post(`http://25.90.180.175:8080/users/createJson`, {}, {
      query: {
        firstname: name,
        secondname: surname,
        login, password, email, role, phone, sex, age, specialty
      }
    })

    window.location.href = "./inter/login";
  }
</script>

<style lang="scss">
  @import "./styles/inter";

  .form__low-data {
    @extend %flex-align-center;
    justify-content: space-between;
  }

  .checkbox-area {
      &__group-name {
        font-size: $medium;
        font-weight: normal;
      }

      &__checkboxes-area {
        display: flex;

        &__label {
          @extend %flex-align-center;

          &:not(:first-child) {
            margin-left: 10px;
          }
        }
      }
    }
</style>

<h1>Регистрация</h1>
<div class="input-area">
  <label for="name" class="input-area__label">Имя</label>
  <br />
  <input
    type="text"
    class="input-area__text-input"
    name="name"
    bind:value={name} />
</div>
<div class="input-area">
  <label for="surname" class="input-area__label">Фамилия</label>
  <br />
  <input
    type="text"
    class="input-area__text-input"
    name="surname"
    bind:value={surname} />
</div>
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
<div class="input-area">
  <label for="phone" class="input-area__label">Телефон</label>
  <br />
  <input
    type="text"
    class="input-area__text-input"
    name="phone"
    bind:value={phone} />
</div>
<div class="input-area">
  <label for="email" class="input-area__label">Email</label>
  <br />
  <input
    type="text"
    class="input-area__text-input"
    name="email"
    bind:value={email} />
</div>
<div class="input-area">
  <label for="specialty" class="input-area__label">Специальность</label>
  <br />
  <input
    type="text"
    class="input-area__text-input"
    name="specialty"
    bind:value={specialty} />
</div>
<div class="form__low-data">
  <div class="input-area">
    <label for="age" class="input-area__label">Возраст</label>
    <br />
    <input
      type="number"
      class="input-area__text-input_age"
      name="age"
      bind:value={age} />
  </div>
  <div class="checkbox-area">
    <h3 class="checkbox-area__group-name">Пол</h3>
    <div class="checkbox-area__checkboxes-area">
      <label
        class="checkbox-area__checkboxes-area__label"
        for="sex">
        муж.
        <input type="radio" name="sex" bind:group={sex} value={"Male"} />
      </label>
      <label
        class="checkbox-area__checkboxes-area__label"
        for="sex">
        жен.
        <input type="radio" name="sex" bind:group={sex} value={"Female"} />
      </label>
    </div>
  </div>
  <div class="checkbox-area">
    <h3 class="checkbox-area__group-name">Роль</h3>
    <div class="checkbox-area__checkboxes-area">
      <label
        class="checkbox-area__checkboxes-area__label"
        for="role">
        заказчик
        <input type="radio" name="role" bind:group={role} value={"Customer"} />
      </label>
      <label
        class="checkbox-area__checkboxes-area__label"
        for="role">
        исполнитель
        <input type="radio" name="role" bind:group={role} value={"Executor"} />
      </label>
    </div>
  </div>
</div>
<button class="form__send_action-button" type="button" on:click={register}>Зарегистрироваться</button>
