<template>
  <div class="main">
    <div class="left-items">
      <img src="../assets/logo.png" alt="logo" @click="actionGoToHome"/>
      <SearchBar class="search-bar"/>
    </div>
    <div class="right-items">
      <div class="upper-items">
        <nav>
          <a @click.prevent="actionGoToPopular">인기 물건</a>
          <a @click.prevent="actionGoToLogin" v-if="!isAuthenticated">로그인</a>
        </nav>
        <div class="user-activity" v-if="isAuthenticated">
          <img class="pencil" src="@/assets/pencil.png"
               @click="actionGoToWritePost">
          <img ref="user-image" class="user-image"
               @click="actionShowSettingModal" src="@/assets/user.png"/>
        </div>
      </div>
      <UserSettingModal ref="user-setting-modal"
                        v-on:editUserInfo="actionShowEditModal"
                        v-on:showMine="actionGoToMine"
                        v-on:showLikes="actionGoToLikes"
                        v-on:logout="actionLogout"/>

    </div>
  </div>
</template>

<script>
import SearchBar from '../components/SearchBar.vue'
import UserSettingModal from '@/components/UserSettingModal'
import userService from '@/services/userService'

export default {
  name: 'MainHeader',
  components: {
    UserSettingModal,
    SearchBar
  },
  computed: {
    isAuthenticated() {
      console.log('[MainHeader] isAuthenticated watcher executed value', this.$store.state.authenticated)
      return this.$store.getters.authenticated
    }
  },
  data() {
    return {}
  },
  methods: {
    loadUserImage() {
      const user = this.$store.state.user
      if (user.imageUrl == null) {
        return require('@/assets/user.png')
      }
      userService.getUserImage(user.imageUrl)
        .then(res => {
          this.$refs['user-image'].src = res
        })
    },
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
    actionGoToPopular() {
      this.$router.push('/popular')
    },
    actionShowSettingModal() {
      this.$refs['user-setting-modal'].show()
    },
    actionGoToMine() {
      this.$router.push('/mine')
    },
    actionShowEditModal() {
      this.$emit('showEditInfoModal')
    },
    actionLogout() {
      userService.logout()
        .then(res => {
          console.log('[Logout]', res)
          this.$store.state.authenticated = false
          this.$store.state.selectedChat = null
          this.$store.state.chats = []
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
    this.emitter.on('getMyDataFinished', () => {
      this.loadUserImage()
    })
  }
}
</script>

<style lang="scss" scoped>

.main {
  padding: 15px 22%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #ffffff;

  .left-items {
    display: flex;
    align-items: center;

    img {
      object-fit: cover;
      height: 35px;
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

      .user-activity {
        display: flex;
        align-items: center;

        .pencil {
          width: 25px;
          margin-left: 20px;
        }

        .user-image {
          width: 35px;
          margin-left: 20px;
          border-radius: 45px;
        }
      }

      :hover {
        cursor: pointer;
      }
    }
  }
}

</style>
