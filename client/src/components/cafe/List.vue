<template>
  <div class="hello">
    <ul class="cafe-list">
      <li v-for="item in list" v-bind:key="item.id" @click="detail(item)">
        <template v-if="item.photos.length > 0">
          <img :src="imgSrc(item.photos[0].name)" class="img"/>
        </template>
        <template v-else>
          {{item.name}}
        </template>
      </li>
    </ul>
    <p v-if="loading">
      loading...
    </p>
    <p v-if="!loading && list.length==0">No Data</p>
  </div>
</template>

<script>
import {cafes} from '@/api/api'
export default {
  name: 'CafeList',
  data () {
    return {
      loading: true,
      list: []
    }
  },
  created: function () {
    cafes.select().then(res => {
      // setTimeout(() => {
      this.loading = false
      this.list = res
      // }, 500)
    })
  },
  methods: {
    detail: function (item) {
      this.$router.push('/cafe/' + item.id)
    },
    imgSrc: function (fileName) {
      return require('@/assets/images/' + fileName)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

.cafe-list li{
  list-style: none;
  display:inline-block;
  width:10em;
  height:10em;
  line-height:10em;
  vertical-align: top;
  border:1px solid black;
  cursor:pointer;
}

.cafe-list li+li {
  margin-left:10px;
}

.img {
  width:100%;
}
</style>
