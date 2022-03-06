<template>
  <div class="main">
      <div class="left-items">
        <img src="../assets/logo.png" alt="logo" @click="actionGoToHome"/>
        <SearchBar class="search-bar" v-if="!isLoginPage"/>
      </div>
      <nav v-if="!isLoginPage">
          <a href="#">About</a>
          <a href="login">Login</a>
      </nav>
  </div>
</template>

<script>
import SearchBar from '../components/SearchBar.vue'

export default {
  name: 'MainHeader',
  components: {
    SearchBar
  },
  data() {
    return {
      isLoginPage: false
    }
  },
  methods: {
    actionGoToHome() {
      this.isLoginPage = false
      this.$router.push('/')
    }
  },
  mounted() {
    this.isLoginPage = window.location.pathname === '/login'
  }
}
</script>

<style lang="scss" scoped>

.main {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #ffffff;

    .left-items {
        display: flex;
        align-items: center;

        img {
            object-fit: cover;
            height: 40px;
            margin-right: 10px;
        }

        img:hover {
            cursor: pointer;
        }

        .search-bar {
            width: 250px;
        }
    }

    nav {
        a {
            font-size: 18px;
            text-decoration: none;
            color: black;
            margin-right: 15px;
        }
    }
}

</style>
