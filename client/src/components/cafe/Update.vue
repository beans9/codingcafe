<template>
  <div class="hello">
    {{title}}
    <p>ID: {{id}}</p>
    <p>카페명: <input type="text" v-model="cafe.name"/></p>
    <P>설명: <textarea v-model="cafe.memo"></textarea></P>
    <input type="button" @click="proc()" value="저장"/>
    <input type="button" @click="back()" value="뒤로"/>
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
        name: '',
        memo: ''
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
    },
    back: function () {
      this.$router.go(-1)
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
