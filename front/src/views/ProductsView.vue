<template>
  <div class="main">
      <h1 class="description">중고 매물</h1>
      <div class="board">
        <ProductCard class="product-card" v-for="product in products" :key="product.id" :product="product"/>
      </div>
  </div>
</template>
<script>
import ProductCard from '../components/ProductCard.vue'
import Product from '../models/Product'

export default {
  name: 'ProductsView',
  components: {
    ProductCard
  },
  data () {
    return {
      products: []
    }
  },
  created () {
    const product = new Product()
    product.name = 'Melon'
    product.price = 30_000
    product.interestCount = 10
    product.chatCount = 7
    product.pictureUrl = '../assets/logo.png'
    product.location = 'Seongdonggu'

    for (let i = 0; i < 20; i++) {
      this.products.push(product)
    }
  }
}
</script>
<style lang="scss" scoped>
.main {
    height: 100%;
    padding: 50px 0px;
    background: #f8f8f8;

    .description {
        margin-bottom: 40px;
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
