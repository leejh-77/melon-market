<template>
  <div class="main">
    <form class="main-form" @submit.prevent="actionDone">
      <span class="description">안 쓰는 물건을 올리세요!</span>
      <ImageContainer :files="images" @deleteImage="deleteImage(idx)" ref="image-container" class="image-container"/>
      <label class="add-button" for="upload-photo">사진 추가하기</label>
      <input type="file" ref="picture-input" id="upload-photo" @change="updateImageContainer" accept=".png, .jpg, .jpeg"
             multiple/>
      <label>제목</label>
      <input class="title" type="text" v-model="title"/>
      <label>가격</label>
      <input class="price" type="number" v-model="price"/>
      <label>설명</label>
      <textarea class="body" v-model="body"/>
      <button class="done-button">완료</button>
    </form>
  </div>
</template>

<script>
import ImageContainer from '@/components/ImageContainer'
import postService from '@/services/postService'

export default {
  name: 'PostEditView',
  components: {
    ImageContainer
  },
  data() {
    return {
      images: [],
      rawFiles: [],
      title: '',
      price: 0,
      body: ''
    }
  },
  methods: {
    actionDone() {
      if (this.title.length === 0) {
        alert('제목은 한 글자 이상이어야 합니다')
        return
      }
      if (this.rawFiles.length === 0) {
        alert('최소 하나의 사진이 첨부되어야 합니다!')
        return
      }

      const formData = new FormData()
      formData.append('title', this.title)
      formData.append('body', this.body)
      formData.append('price', this.price)

      for (let i = 0; i < this.rawFiles.length; i++) {
        formData.append('images', this.rawFiles[i])
      }

      postService.addPost(formData)
        .then(res => {
          this.$router.push('/')
        })
        .catch(e => {
          alert('글 작성에 실패했습니다')
        })
    },
    updateImageContainer() {
      const files = this.$refs['picture-input'].files
      this.rawFiles.push(...files)

      for (let i = 0; i < files.length; i++) {
        const reader = new FileReader()
        reader.onload = (event) => {
          this.images.push(event.target.result)
        }
        reader.readAsDataURL(files.item(i))
      }
    },
    deleteImage(idx) {
      this.images.splice(idx, 1)
      this.rawFiles.splice(idx, 1)
    }
  }
}
</script>

<style lang="scss" scoped>

.main {
  .main-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px 0;

    .description {
      font-size: 30px;
      margin-top: 20px;
      margin-bottom: 20px;
      font-weight: bold;
    }

    .image-container {
      margin-top: 20px;
      margin-bottom: 30px;
    }

    .add-button {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 200px;
      height: 40px;
      margin-bottom: 20px;
      color: black;
      border-radius: 10px;
      font-weight: bold;
      background-color: lightgray;
    }

    .add-button:hover {
      cursor: pointer;
    }

    #upload-photo {
      display: none;
    }

    .title {
      width: 40%;
      height: 45px;
      font-size: 18px;
      border-radius: 10px;
      border: 1px solid lightgray;
      padding-left: 15px;
      margin-bottom: 20px;
    }

    .body {
      width: 40%;
      min-height: 200px;
      font-size: 15px;
      border-radius: 10px;
      border: 1px solid lightgray;
      padding: 8px;
      margin-bottom: 20px;
    }

    .done-button {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 200px;
      height: 40px;
      margin-bottom: 20px;
      border: 1px solid cornflowerblue;
      background: cornflowerblue;
      color: white;
      border-radius: 10px;
      font-weight: bold;
    }

    .done-button:hover {
      cursor: pointer;
    }

    label {
      margin-bottom: 10px;
      font-weight: bold;
    }

    .price {
      width: 40%;
      height: 25px;
      font-size: 18px;
      border-radius: 10px;
      border: 1px solid lightgray;
      padding: 8px;
      margin-bottom: 20px;
    }
  }
}

</style>
