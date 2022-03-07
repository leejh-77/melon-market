<template>
  <div class="main">
    <form class="main-form" @submit.prevent="actionLogin">
      <span class="main-title">Melon market</span>
      <input v-model="emailAddress" type="email" placeholder="EmailAddress">
      <input v-model="password" type="password" placeholder="Password">
      <div class="main-buttons">
        <button class="btn-login">Login</button>
      </div>
    </form>
    <button class="btn-signup" @click="actionMoveToSignup">Signup</button>
  </div>
</template>
<script>
import userService from '@/services/userService'

export default {
  name: 'LoginView',
  data() {
    return {
      emailAddress: '',
      password: ''
    }
  },
  methods: {
    actionLogin() {
      if (!this.validateInputs()) {
        return
      }
      userService.login(this.emailAddress, this.password)
        .then(res => {
          this.$router.push('/')
        })
        .catch(e => {
          alert('Failed to login. Check your emailAddress and password')
        })
    },
    actionMoveToSignup() {
      this.$router.push('/signup')
    },
    validateInputs() {
      const regex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
      if (!this.emailAddress.match(regex)) {
        alert('Invalid email address')
        return false
      }
      if (this.password.length < 6) {
        alert('Password must be longer than 5 characters')
        return false
      }
      return true
    }
  }
}
</script>
<style lang="scss" scoped>

.main {
  display: flex;
  flex-direction: column;
  height: 100%;
  background: #f8f8f8;
  padding: 30px 0;

  .main-title {
    color: seagreen;
    font-weight: bold;
    margin-bottom: 20px;
    font-size: 30px;
  }

  .main-form {
    display: flex;
    flex-direction: column;
    height: 100%;
    background: #f8f8f8;

    input {
      padding-left: 10px;
      align-self: center;
      width: 500px;
      height: 40px;
      margin: 5px;
      border: 1px solid lightgray;
      border-radius: 7px;
    }
  }
}

.btn-login {
  width: 250px;
  align-self: center;
  height: 40px;
  margin: 5px;
  color: white;
  border: 1px solid cornflowerblue;
  border-radius: 7px;
  background-color: cornflowerblue;
}

button:hover {
  cursor: pointer;
}

.btn-signup {
  width: 250px;
  align-self: center;
  height: 40px;
  margin: 5px;
  color: cornflowerblue;
  border: 1px solid cornflowerblue;
  border-radius: 7px;
  background-color: white;
}

</style>
