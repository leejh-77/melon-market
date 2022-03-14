<template>
  <div class="main">
    <form class="main-form" @submit.prevent="actionDone">
      <span class="description">안 쓰는 물건을 올리세요!</span>
      <ImageContainer :files="imageData" @deleteImage="deleteImage" ref="image-container"
                      class="image-container"/>
      <label class="add-button" for="upload-photo">사진 추가하기</label>
      <input type="file" ref="picture-input" id="upload-photo" @change="updateImageContainer" accept=".png, .jpg, .jpeg"
             multiple/>
      <label>지역</label>
      <RegionSelector @onSelectRegion="onSelectRegion"/>
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
import RegionSelector from '@/components/RegionSelector'

export default {
  name: 'PostEditView',
  components: {
    ImageContainer,
    RegionSelector
  },
  data() {
    return {
      images: [
        /**
         data
         url
         deleted
         file
         * */
      ],
      imageData: [],
      title: '',
      price: 0,
      body: '',
      id: null,
      region: null
    }
  },
  methods: {
    actionDone() {
      if (this.title.length === 0) {
        alert('제목은 한 글자 이상이어야 합니다')
        return
      }
      if (this.imageData.length === 0) {
        alert('최소 하나의 사진이 첨부되어야 합니다!')
        return
      }
      if (this.region == null || this.region.district == null) {
        alert('지역을 설정해주세요!')
        return
      }

      const formData = new FormData()
      formData.append('title', this.title)
      formData.append('body', this.body)
      formData.append('price', this.price)
      formData.append('region', this.region.code)

      for (let i = 0; i < this.images.length; i++) {
        const image = this.images[i]
        if (image.file != null) {
          formData.append('images', image.file)
        } else if (image.deleted) {
          formData.append('deletedImages', image.url)
        }
      }

      console.log('[EditPost] Title - ', formData.getAll('title'))
      console.log('[EditPost] Body - ', formData.getAll('body'))
      console.log('[EditPost] Price - ', formData.getAll('price'))
      console.log('[EditPost] AddedImages - ', formData.getAll('images'))
      console.log('[EditPost] DeletedImages - ', formData.getAll('deletedImages'))

      if (this.id === null) {
        postService.addPost(formData)
          .then(res => {
            this.$router.push('/post-detail/' + res.data)
          })
          .catch(e => {
            alert('글 작성에 실패했습니다')
          })
      } else {
        postService.updatePost(this.id, formData)
          .then(res => {
            this.$router.push('/post-detail/' + this.id)
          })
          .catch(e => {
            alert('글 수정에 실패했습니다')
          })
      }
    },
    updateImageContainer() {
      const files = this.$refs['picture-input'].files

      for (let i = 0; i < files.length; i++) {
        const reader = new FileReader()
        reader.onload = (event) => {
          this.images.push({
            data: event.target.result,
            deleted: false,
            file: files[i],
            url: null
          })
          this.makeImageData()
        }
        reader.readAsDataURL(files.item(i))
      }
    },
    deleteImage(idx) {
      const image = this.images[idx]
      console.log('[Delete Image]', idx, image)
      if (image.file != null) {
        this.images.splice(idx, 1)
      } else {
        image.deleted = true
      }
      this.makeImageData()
    },
    makeImageData() {
      this.imageData = this.images
        .filter(image => !image.deleted)
        .map(image => image.data)
    },
    fillData(postId) {
      console.log('[EditPost] postId : ' + postId)
      postService.getPost(postId)
        .then(res => {
          console.log('[GetPostData]', res.data)

          const post = res.data.post
          this.id = post.id
          this.title = post.title
          this.body = post.body
          this.price = post.price

          post.imageUrls.forEach(url => {
            postService.getPostImage(url)
              .then(res => {
                console.log('[GetPostImage]', url)
                this.images.push({
                  data: res,
                  deleted: false,
                  url: url,
                  file: null
                })
                this.makeImageData()
              })
          })
        })
        .catch(e => {
          alert('글을 불러오지 못했습니다')
        })
    },
    onSelectRegion(region) {
      this.region = region
    }
  },
  mounted() {
    window.scrollTo(0, 0)

    const postId = this.$route.params.postId
    if (postId == null) {
      return // new post
    }
    this.fillData(postId)
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
