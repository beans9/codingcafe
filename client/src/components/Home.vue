<template>
  <div class="hello">
    <div class="menu">
      <router-link to="/cafe/write">입력</router-link>
      <router-link to="/user/signin">로그인</router-link>
      <router-link to="/user/signup">회원가입</router-link>
    </div>
    <CafeList></CafeList>
  </div>
</template>

<script>
import CafeList from '@/components/cafe/List.vue'

export default {
  name: 'Home',
  components: {CafeList},
  data () {
    return {}
  },
  created: function () {
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
</style>
