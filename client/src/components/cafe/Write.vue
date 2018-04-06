<template>
  <div class="hello">
    {{title}}
    <p>카페명: <input type="text" v-model="cafe.name"/></p>
    <input type="button" @click="proc()" value="저장"/>
  </div>
</template>

<script>
import {cafes} from '@/api/api'
export default {
  name: 'CafeWrite',
  data () {
    return {
      title: '입력',
      cafe: {
        name: ''
      },
      list: []
    }
  },
  created: function () {},
  methods: {
    proc: function () {
      cafes.insert(this.cafe).then((res) => {
        this.$router.push('/cafe/' + res.id)
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
