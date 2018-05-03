<template>
  <div class="hello">
    상세화면
    <p>
      게시물ID: {{id}}
    </p>
    <p v-if="cafe.photos.length != 0">
      이미지
      <img :src="imgSrc(photo.name)" class="img" v-for="(photo,index) in cafe.photos" :key="index"/>
    </p>
    <p>
      작성자: {{cafe.appUser.username}}
    </p>
    <p>
      이름 : {{cafe.name}}
    </p>
    <p>
      주소 : {{cafe.address}}
    </p>
    <p>주차정보: {{cafe.parking===true?'가능':'불가능'}}</p>
    <p>무선랜정보: {{cafe.wifi===true?'가능':'불가능'}}</p>
    <p>전원콘센트: {{cafe.concent===true?'있음':'없음'}}</p>
    <p>재방문의사: {{cafe.reVisit===true?'있음':'없음'}}</p>
    <P>기타설명: {{cafe.memo}}</P>
    <p>태그:</p>
    <template v-if="isModified">
      <input type="button" @click="patch()" value="수정"/>
      <input type="button" @click="del()" value="삭제"/>
    </template>
    <router-link to="/">리스트</router-link>
  </div>
</template>

<script>
import {cafes} from '@/api/api'
import {mapGetters} from 'vuex'

export default {
  name: 'CafeDetail',
  props: ['id'],
  data () {
    return {
      cafe: {
        name: '',
        nameErr: false,
        memo: '',
        address: '',
        addressErr: false,
        parking: 0,
        concent: 0,
        wifi: 0,
        reVisit: 0,
        tag: '',
        appUser: {
          username: ''
        },
        photos: []
      }
    }
  },
  beforeRouteEnter (to, from, next) {
    cafes.get(to.params.id).then((res) => next(vm => { vm.cafe = res }))
      .catch((e) => next(false))
  },
  watch: {
    '$route': function (from, to) { this.init() }
  },
  created: function () {},
  methods: {
    init: function () {
      cafes.get(this.id).then((res) => {
        this.cafe = res
      }).catch((e) => {
        this.$router.push('/')
      })
    },
    patch: function () {
      this.$router.push('/cafe/' + this.id + '/update')
    },
    del: function () {
      cafes.del(this.id).then(() => {
        this.$router.push('/')
      })
    },
    imgSrc: function (fileName) {
      return require('@/assets/images/' + fileName)
    }
  },
  computed: {
    ...mapGetters([
      'isAuthenticated', 'getUserInfo'
    ]),
    isModified () {
      return this.getUserInfo.id === this.cafe.appUser.id
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

.img {
  width:200px;
  border:1px solid #e6e6e6;
}
</style>
