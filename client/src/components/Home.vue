<template>
  <div class="main">
    <div class="menu">
      <div class="menu-content">
        <div class="logo">
          <!-- CoadingCaffe -->
          CC - -
        </div>
        <div class="menu-right">
          <router-link to="/user/signin" v-if="!isAuthenticated">로그인</router-link>
          <router-link to="/user/signup" v-if="!isAuthenticated">회원가입</router-link>
          <router-link to="/cafe/new" v-if="isAuthenticated" class="btn info">입력</router-link>
          <router-link to="/user/info" v-if="isAuthenticated" class="btn">{{getUserInfo.name}}</router-link>
          <!-- <a href="#" @click="logout()" v-if="isAuthenticated" class="btn">로그아웃</a> -->
        </div>
      </div>
    </div>

    <router-view></router-view>

    <!-- <img src="@/assets/images/logo.png" /> -->
    <!-- <p v-if="isAuthenticated">{{getUserInfo.name}}({{getUserInfo.email}})님 안녕하세요.</p> -->
    <div class="footer">
      <div class="footer-content">
          © CoadingCaffe. All rights reserved
      </div>
    </div>
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
<style lang="scss" scoped>
@import "~styles/common.scss";
@import "~styles/_mixin.scss";

.main {
  .menu {
    background-color: white;
    border-bottom:1px solid #d1d8e5;

    .menu-content {
      // border:1px solid red;
      max-width: 1280px;
      width:100%;
      margin:0 auto;
      height: 50px;
      line-height:50px;

      .logo {
        float:left;
        padding-left:50px;
        @include respond-to('small') {
          padding-left:20px;
        }
        font-weight: bold;
        font-size:20px;
      }
      .menu-right {
        float:right;
        padding-right:50px;
        @include respond-to('small') {
          padding-right:20px;
        }
        font-size:14px;
      }

    }
  }
}

.footer {
  clear:both;

  display:inline-block;
  margin-top:30px;
  width:100%;
  background-color:#f6f6f6;
  padding-top:50px;
  padding-bottom:80px;

  @include respond-to('small') {
    padding-top:20px;
    padding-bottom:20px;
  }

  text-align: left;

  .footer-content {
    // border:1px solid red;
    width:1280px;
    margin:0 auto;
    padding-left:110px;
    font-size:13px;
    box-sizing: border-box;

    color:rgba(88, 88, 88, 0.5);
    @include respond-to('large') {
      width:95%;
    }

    @include respond-to('small') {
      padding-left:25px;
      font-size:12px;
    }
  }
}
</style>
