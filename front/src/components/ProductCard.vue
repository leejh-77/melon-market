<template>
  <div class="container">
      <img class="picture" src="../assets/logo.png"/>
      <p class="name">{{ product.name }}</p>
      <p class="price">{{ product.price }} won</p>
      <p class="location">{{ product.location }}</p>
      <div class="extra">
        <p class="interests">interests {{ product.interestCount }}</p>
        <p>*</p>
        <p class="chats">chats {{ product.chatCount }}</p>
      </div>
  </div>
</template>
<script>
export default {
  name: 'ProductCard',
  props: {
    product: null
  }
}
</script>
<style lang="scss" scoped>

.container {
    text-align: left;

    p {
        margin-top: 2px;
    }

    .picture {
        width: 220px;
        height: 220px;
        background: white;
        border-radius: 20px;
    }

    .name {
        font-size: 20px;
        color: black;
    }

    .price {
        color: black;
        font-weight: bold;
    }

    .location {
        color: black;
        font-size: 13px;
    }

    .extra {
        display: flex;

        p {
            margin-right: 5px;
            color: gray;
        }
    }
}

</style>
