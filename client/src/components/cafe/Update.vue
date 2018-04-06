<template>
  <div class="hello">
    {{title}}
    <p>ID: {{id}}</p>
    <p>카페명: <input type="text" v-model="cafe.name"/></p>
    <input type="button" @click="proc()" value="저장"/>
  </div>
</template>

<script>
import {cafes} from '@/api/api'
export default {
  name: 'CafeUpdate',
  props: ['id'],
  data () {
    return {
      title: '수정',
      cafe: {
        id: null,
        name: ''
      },
      list: []
    }
  },
  created: function () {
    this.get()
  },
  methods: {
    get: function () {
      cafes.get(this.id).then((res) => {
        this.cafe = res
      })
    },
    proc: function () {
      cafes.patch(this.cafe).then((res) => {
        this.$router.replace('/cafe/' + res.id)
      })
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
