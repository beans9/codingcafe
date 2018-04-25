<template>
  <div class="hello">
    <p v-if="isAuthenticated">{{getUserInfo.name}}({{getUserInfo.email}})님 안녕하세요.</p>
    <div class="menu">
      <router-link to="/cafe/new" v-if="isAuthenticated">입력</router-link>
      <router-link to="/user/signin" v-if="!isAuthenticated">로그인</router-link>
      <router-link to="/user/signup" v-if="!isAuthenticated">회원가입</router-link>
      <router-link to="/user/info" v-if="isAuthenticated">회원정보</router-link>
      <a href="#" @click="logout()" v-if="isAuthenticated">로그아웃</a>
    </div>
    <CafeList></CafeList>
  </div>
</template>

<script>
import CafeList from '@/components/cafe/List.vue'
// import {EventBus} from '@/event-bus.js'
import {mapGetters} from 'vuex'

export default {
  name: 'Home',
  components: {CafeList},
  data () {
    return {}
  },
  created: function () {},
  methods: {
    logout: function () {
      delete localStorage.codingcafeToken
      this.$store.dispatch('LOGOUT')
    }
  },
  computed: {
    ...mapGetters([
      'isAuthenticated', 'getUserInfo'
    ])
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
</style>
