<template>
  <div class="main">
    <div class="left-items">
      <img src="../assets/logo.png" alt="logo" @click="actionGoToHome"/>
      <SearchBar class="search-bar"/>
    </div>
    <div class="right-items">
      <div class="upper-items">
        <nav>
          <a href="#">About</a>
          <a href="login" v-show="!isAuthenticated">Login</a>
        </nav>
        <img ref="user-image" class="user-image" v-show="isAuthenticated"
             @click="actionShowSettingModal" src="../assets/user.png"/>
      </div>
      <UserSettingModal ref="user-setting-modal"
                        v-on:changePicture="actionChangePicture"
                        v-on:writePost="actionGoToWritePost"/>
    </div>
  </div>
</template>

<script>
import SearchBar from '../components/SearchBar.vue'
import UserSettingModal from '@/components/UserSettingModal'

export default {
  name: 'MainHeader',
  components: {
    UserSettingModal,
    SearchBar
  },
  computed: {
    isAuthenticated() {
      return this.$store.getters.getUser.authenticated
    }
  },
  data() {
    return {}
  },
  methods: {
    actionGoToHome() {
      this.$router.push('/')
    },
    actionShowSettingModal() {
      this.$refs['user-setting-modal'].show()
    },
    actionGoToWritePost() {
      console.log('write post')
    },
    actionChangePicture() {
      console.log('change picture')
    }
  },
  watch: {
    isAuthenticated(value) {
      if (value) {
        const img = this.$store.state.user.imagePath
        if (img != null) {
          this.$refs['user-image'].src = img
        }
      }
    }
  },
  mounted() {
    if (!this.$store.state.user.authenticated) {
      this.$store.dispatch('getMyData')
    }
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

  .right-items {
    .upper-items {
      display: flex;
      align-items: center;

      nav {
        a {
          font-size: 18px;
          text-decoration: none;
          color: black;
          margin-right: 15px;
        }
      }

      .user-image {
        width: 35px;
        margin-left: 50px;
        border-radius: 45px;
      }

      :hover {
        cursor: pointer;
      }
    }
  }
}

</style>
