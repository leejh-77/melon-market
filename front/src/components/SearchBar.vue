<template>
    <div class="wrap">
        <div class="search-container">
            <input class="searchTerm" type="text" placeholder="검색"/>
        </div>
    </div>
</template>

<script>
export default {
  name: 'SearchBar'
}
</script>

<style lang="scss" scoped>

.search-container {
    width: 100%;

    .searchTerm {
        width: 100%;
        border: 1px solid lightgray;
        padding: 7px;
        padding-left: 10px;
        height: 20px;
        border-radius: 10px;
        outline: none;
        color: #9DBFAF;

        background-image: url("../assets/search-bar-icon.png");
        background-repeat: no-repeat;
        background-size: 22px;
        background-position: 97% center;
    }
}

</style>
