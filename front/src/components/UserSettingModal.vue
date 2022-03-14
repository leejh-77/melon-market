<template>
  <div class="modal" v-show="isShowing">
    <span>{{ $store.state.user.username }}</span>
    <button @click="$emit('showMine'); close()">내가 올린 물건</button>
    <button @click="$emit('editUserInfo'); close()">정보 변경</button>
    <button @click="$emit('showLikes'); close()">좋아요 보기</button>
    <button class="white-button" @click="$emit('logout'); close()">로그아웃</button>
    <button class="white-button" @click="close">닫기</button>
  </div>
</template>

<script>
export default {
  name: 'UserSettingModal',
  data() {
    return {
      isShowing: false
    }
  },
  methods: {
    show() {
      this.isShowing = true
    },
    close() {
      this.isShowing = false
    }
  }
}
</script>

<style lang="scss" scoped>
.modal {
  position: absolute;
  display: flex;
  flex-direction: column;
  margin-top: 10px;
  width: 200px;
  border: 1px solid lightgray;
  background: white;
  border-radius: 10px;
  padding: 20px;

  span {
    margin-bottom: 20px;
    font-weight: bold;
  }

  button {
    width: 100%;
    height: 40px;
    font-size: 15px;
    border: 1px solid lightgray;
    border-radius: 10px;
    margin-bottom: 5px;
  }

  button:hover {
    cursor: pointer;
  }

  .white-button {
    width: 100%;
    height: 40px;
    font-size: 15px;
    border: 1px solid lightgray;
    background: white;
    border-radius: 10px;
    margin-bottom: 5px;
  }
}
</style>
