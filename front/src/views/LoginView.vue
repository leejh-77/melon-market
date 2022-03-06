<template>
  <div class="main">
    <form class="main-form" @submit.prevent="actionLogin">
      <input type="email" placeholder="EmailAddress">
      <input type="password" placeholder="Password">
      <input type="password" placeholder="Confirm Password">
      <button>Login</button>
    </form>
  </div>
</template>
<script>
export default {
  name: 'LoginView',
  data() {
    return {
      emailAddress: '',
      password: '',
      confirmPassword: ''
    }
  },
  methods: {
    actionLogin() {
      const format = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/
      if (!this.emailAddress.match(format)) {
        alert('Invalid email address')
        return
      }

      if (this.password.length < 6) {
        alert('Password must be longer than 5 characters')
        return
      }
      if (this.confirmPassword !== this.password) {
        alert('Password must be longer than 5 characters')
      }
    }
  }
}
</script>
<style lang="scss" scoped>

.main-form {
  display: flex;
  flex-direction: column;
  height: 100%;
  background: #f8f8f8;
  padding: 50px 0;

  input {
    padding-left: 10px;
    align-self: center;
    width: 500px;
    height: 40px;
    margin: 5px;
    border: 1px solid lightgray;
    border-radius: 7px;
  }

  button {
    width: 250px;
    align-self: center;
    height: 40px;
    margin: 5px;
  }
}
</style>
