<template>
  <div class="main">
    <div class="left-items">
      <img src="../assets/logo.png" alt="logo" @click="actionGoToHome"/>
      <SearchBar class="search-bar" v-show="!showOnlyHomeButton"/>
    </div>
    <nav v-if="!showOnlyHomeButton">
      <a href="#">About</a>
      <a href="login" v-show="!isAuthenticated">Login</a>
    </nav>
  </div>
</template>

<script>
import SearchBar from '../components/SearchBar.vue'
import userService from '@/services/userService'

export default {
  name: 'MainHeader',
  components: {
    SearchBar
  },
  computed: {
    isAuthenticated() {
      return this.$store.getters.isAuthenticated
    },
    showOnlyHomeButton() {
      return window.location.pathname === '/login' || window.location.pathname === '/signup'
    }
  },
  data() {
    return {}
  },
  methods: {
    actionGoToHome() {
      this.$router.push('/')
    }
  },
  mounted() {
    userService.getMyData()
      .then(res => {
        if (res.status === 200) {
          this.$store.dispatch('setAuthenticated', true)
        }
      })
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
