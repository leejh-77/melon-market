<template>
  <div class="main">
    <h1 class="title">{{ getTitle }}</h1>
    <RegionSelector class="region-selector"
                    @onSelectRegion="onSelectRegion"/>
    <div class="board">
      <PostCard v-on:onClickImage="onClickImage(post.id)"
                class="post-card" v-for="post in posts" :key="post.id"
                :post="post"/>
    </div>
  </div>
</template>
<script>
import PostCard from '../components/PostCard.vue'
import postService from '@/services/postService'
import { ListQuery } from '@/constant'
import RegionSelector from '@/components/RegionSelector'

export default {
  name: 'PostsBoard',
  props: ['type'],
  components: {
    RegionSelector,
    PostCard
  },
  computed: {
    getTitle() {
      const type = this.getListType()
      switch (type) {
        case ListQuery.Recent:
          return '최근 매물'
        case ListQuery.Like:
          return '좋아요한 매물'
        case ListQuery.Popular:
          return '인기 매물'
        default:
          return ''
      }
    }
  },
  data() {
    return {
      posts: [],
      region: null,
      searchText: null
    }
  },
  methods: {
    onClickImage(id) {
      this.$router.push('/post-detail/' + id)
    },
    getListType() {
      return this.type ?? ListQuery.Recent
    },
    loadList() {
      const type = this.getListType()
      let queryText = this.searchText
      if (type !== ListQuery.Recent) {
        queryText = null
      }
      let regionCode
      if (this.region != null) {
        if (this.region.district.length > 0) {
          regionCode = this.region.code
        } else if (this.region.town.length > 0) {
          regionCode = this.region.code.substring(0, 4)
        } else {
          regionCode = this.region.code.substring(0, 2)
        }
      }
      console.log('[GetPostList][Req] type :' + type + ', queryText : ' + queryText, ', region : ' + regionCode)
      postService.getPostList(type, queryText, regionCode)
        .then(res => {
          console.log('[GetPostList]', res.data)
          this.posts = res.data
        })
    },
    onSelectRegion(region) {
      this.region = region
      this.loadList()
    }
  },
  mounted() {
    this.emitter.on('update-queryText', text => {
      console.log('[PostBoard] queryText - ')
      this.searchText = text
      this.loadList()
    })
    this.loadList()
  }
}

</script>
<style lang="scss" scoped>
.main {
  height: 100%;
  padding: 50px 0;
  background: #f8f8f8;

  .title {
    margin-bottom: 15px;
  }

  .region-selector {
    padding: 5px;
    margin-bottom: 40px;
    text-align: end;
    margin-right: 10%;
  }

  .board {
    display: grid;
    justify-content: center;
    grid-template-columns: repeat(4, 220px);
    column-gap: 60px;
    row-gap: 50px;
  }
}
</style>
