<template>
  <div v-if="isShowing">
    <div class="background" @click="hide">
    </div>
    <form class="main" @submit.prevent="actionDone">
      <img class="close-button" src="@/assets/close.png" @click="hide">
      <img class="user-image" ref="user-image" src="@/assets/user.png">
      <label class="upload-file" for="file-input">사진 업로드</label>
      <input ref="file-input" id="file-input" type="file" hidden="hidden" @change="onPhotoSelected" accept="image/*"/>
      <input class="username" v-model="user.username"/>
      <button>Done</button>
    </form>
  </div>
</template>

<script>
import userService from '@/services/userService'

export default {
  name: 'UserInfoModal',
  data() {
    return {
      isShowing: false,
      user: {
        imageUrl: null,
        username: null
      },
      rawFile: null
    }
  },
  methods: {
    show() {
      this.isShowing = true
      this.fillData()
    },
    hide() {
      this.isShowing = false
    },
    fillData() {
      const user = this.$store.state.user
      this.user.imageUrl = user.imageUrl
      this.user.username = user.username

      if (this.user.imageUrl == null) {
        return
      }
      userService.getUserImage(this.user.imageUrl)
        .then(res => {
          this.$refs['user-image'].src = res
        })
    },
    onPhotoSelected() {
      const file = this.$refs['file-input'].files[0]
      const reader = new FileReader()
      reader.onload = (event) => {
        this.$refs['user-image'].src = event.target.result
      }
      reader.readAsDataURL(file)
      this.rawFile = file
    },
    actionDone() {
      if (this.user.username.length === 0) {
        alert('이름은 0자 이상이어야 합니다')
        return
      }
      const form = new FormData()
      form.append('username', this.user.username)

      if (this.rawFile != null) {
        form.append('image', this.rawFile)
      }
      userService.updateUser(form)
        .then(res => {
          this.$store.dispatch('getMyData')
          this.hide()
        })
    }
  }
}
</script>

<style lang="scss" scoped>

.background {
  position: absolute;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, .5);
  opacity: 0.3;
  z-index: 2;
  overflow: hidden;
}

.main {
  border-radius: 20px;
  background: white;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 400px;
  height: 400px;
  z-index: 3;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  .close-button {
    position: absolute;
    right: 0;
    top: 0;
    margin: 20px;
    width: 20px;
  }

  .close-button:hover {
    cursor: pointer;
  }

  .user-image {
    width: 150px;
    height: 150px;
    margin-bottom: 10px;
    border-radius: 50px;
    object-fit: cover;
  }

  .upload-file:hover {
    cursor: pointer;
  }

  input {
    margin-top: 10px;
    border: 1px solid lightgray;
    border-radius: 5px;
    text-align: center;
    padding: 5px;
    font-size: 18px;
    width: 200px;
    margin-bottom: 20px;
  }

  button {
    width: 150px;
    height: 35px;
    border: 0;
    background-color: cornflowerblue;
    color: white;
    border-radius: 10px;
  }

  button:hover {
    cursor: pointer;
  }
}

</style>
