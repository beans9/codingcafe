<template>
  <div class="hello">
    <ul>
      <li v-for="item in list" v-bind:key="item.id" @click="detail(item)">{{item.name}}</li>
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
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
</style>
