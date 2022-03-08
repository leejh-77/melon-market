<template>
  <div class="main">
    <form class="main-form" @submit.prevent="actionLogin">
      <span class="main-title">멜론마켓</span>
      <input v-model="emailAddress" type="email" placeholder="이메일">
      <input v-model="username" type="text" placeholder="이름">
      <input v-model="password" type="password" placeholder="비밀번호">
      <input v-model="confirmPassword" type="password" placeholder="확인 비밀번호">
      <div class="main-buttons">
        <button class="btn-signup">Signup</button>
      </div>
    </form>
  </div>
</template>
<script>
import userService from '@/services/userService'

export default {
  name: 'SignupView',
  data() {
    return {
      emailAddress: '',
      username: '',
      password: '',
      confirmPassword: ''
    }
  },
  methods: {
    actionLogin() {
      if (!this.validateInputs()) {
        return
      }
      userService.signup(this.emailAddress, this.username, this.password)
        .then(res => {
          this.$router.push('/login')
        })
        .catch(e => {
          alert('회원가입에 실패했습니다')
        })
    },
    validateInputs() {
      const regex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
      if (!this.emailAddress.match(regex)) {
        alert('올바르지 않은 이메일 형식입니다')
        return false
      }
      if (this.password.length < 6) {
        alert('비밀번호는 6자 이상이어야 합니다')
        return false
      }
      if (this.confirmPassword !== this.password) {
        alert('확인 비밀번호가 올바르지 않습니다')
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
