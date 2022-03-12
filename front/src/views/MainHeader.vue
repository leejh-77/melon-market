<template>
  <div class="main">
    <div class="left-items">
      <img src="../assets/logo.png" alt="logo" @click="actionGoToHome"/>
      <SearchBar class="search-bar"/>
    </div>
    <div class="right-items">
      <div class="upper-items">
        <nav>
          <a @click.prevent="actionGoToLogin" v-show="!isAuthenticated">Login</a>
        </nav>
        <img ref="user-image" class="user-image" v-show="isAuthenticated"
             @click="actionShowSettingModal" src="../assets/user.png"/>
      </div>
      <UserSettingModal ref="user-setting-modal"
                        v-on:changeInfo="actionChangeInfo"
                        v-on:writePost="actionGoToWritePost"
                        v-on:showLikes="actionGoToLikes"
                        v-on:logout="actionLogout"/>

    </div>
    <UserInfoModal ref="user-info-modal"/>
  </div>
</template>

<script>
import SearchBar from '../components/SearchBar.vue'
import UserSettingModal from '@/components/UserSettingModal'
import UserInfoModal from '@/components/UserInfoModal'
import userService from '@/services/userService'

export default {
  name: 'MainHeader',
  components: {
    UserInfoModal,
    UserSettingModal,
    SearchBar
  },
  computed: {
    isAuthenticated() {
      return this.$store.getters.authenticated
    }
  },
  data() {
    return {}
  },
  methods: {
    actionGoToHome() {
      this.$router.push('/')
    },
    actionGoToLogin() {
      this.$router.push('/login')
    },
    actionGoToWritePost() {
      this.$router.push('/post-edit')
    },
    actionGoToLikes() {
      this.$router.push('/likes')
    },
    actionShowSettingModal() {
      this.$refs['user-setting-modal'].show()
    },
    actionChangeInfo() {
      this.$refs['user-info-modal'].show()
    },
    actionLogout() {
      userService.logout()
        .then(res => {
          console.log('[Logout]', res)
          this.$store.state.authenticated = false
          this.$router.push('/')
        })
        .catch(e => {
          console.log('[Logout]', e)
        })
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
  padding: 15px 20%;
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
        margin-left: 20px;
        border-radius: 45px;
      }

      :hover {
        cursor: pointer;
      }
    }
  }
}

</style>
