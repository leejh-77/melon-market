<template>
  <div class="main">
    <form class="main-form" @submit.prevent="actionDone">
      <span class="description">Sell your stuffs!</span>
      <ImageContainer :files="images" @deleteImage="deleteImage(idx)" ref="image-container" class="image-container"/>
      <label class="add-button" for="upload-photo">Add Picture</label>
      <input type="file" ref="picture-input" id="upload-photo" @change="updateImageContainer" accept=".png, .jpg, .jpeg"
             multiple/>
      <input class="title" type="text" placeholder="title" v-model="title"/>
      <textarea class="body" v-model="body"/>
      <button class="done-button">Done</button>
    </form>
  </div>
</template>

<script>
import ImageContainer from '@/components/ImageContainer'
import postService from '@/services/postService'

export default {
  name: 'WritePostView',
  components: {
    ImageContainer
  },
  data() {
    return {
      images: [],
      rawFiles: [],
      title: '',
      body: ''
    }
  },
  methods: {
    actionDone() {
      if (this.title.length === 0) {
        alert('Title must be long than 0 character')
        return
      }
      if (this.rawFiles.length === 0) {
        alert('At least one picture has to be attached')
        return
      }

      const formData = new FormData()
      formData.append('title', this.title)
      formData.append('body', this.body)
      formData.append('price', '12000')

      for (let i = 0; i < this.rawFiles.length; i++) {
        formData.append('images', this.rawFiles[i])
      }

      postService.addPost(formData)
        .then(res => {
          this.$router.push('/')
        })
        .catch(e => {
          alert('Failed to create post')
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
  }
}

</style>
