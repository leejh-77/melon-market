<template>
  <div class="main">
    <h1>최근 물건</h1>
    <RegionSelector class="region-selector" @onSelectRegion="onSelectRegion" />
    <PostsBoard ref="board" :type="getListType"/>
  </div>
</template>

<script>
import PostsBoard from '@/views/PostsBoard'
import { ListQuery } from '@/constant'
import RegionSelector from '@/components/RegionSelector'

export default {
  name: 'MainBoardView',
  components: { RegionSelector, PostsBoard },
  computed: {
    getListType() {
      return ListQuery.Recent
    }
  },
  methods: {
    onSelectRegion(region) {
      this.$refs.board.onSelectRegion(region)
    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  padding-top: 50px;
  background: #f8f8f8;

  .region-selector {
    padding: 5px;
    text-align: end;
    margin-right: 10%;
  }
}

</style>
