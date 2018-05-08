<template>
  <div class="detail">
    <div class="detail-cover">
      <template v-if="cover != null">
        <img :src="imgSrc(cover.name)" />
        <!-- <img :src="imgSrc(photo.name)" v-for="(photo,index) in cafe.photos" :key="index"/> -->
      </template>
    </div>
    <div class="detail-cover-list">
      <img :src="imgSrcTumb(photo.name)" v-for="(photo,index) in cafe.photos" :key="index" v-bind:class="{active:photo == cover}" @click="changeImg(photo)"/>
    </div>
    <div class="detail-info">
      <h1>{{cafe.name}}</h1>
      <h2>{{cafe.address}}</h2>
      <div class="detail-info-user">
        {{cafe.appUser.username}}
      </div>
    </div>


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
    <router-link to="/cafe">리스트</router-link>
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
      },
      cover: null
    }
  },
  beforeRouteEnter (to, from, next) {
    cafes.get(to.params.id).then((res) => next(vm => {
      vm.cafe = res
      if (vm.cafe.photos.length > 0) {
        vm.cover = vm.cafe.photos[0]
      }
    }))
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
    changeImg: function (photo) {
      this.cover = photo
    },
    imgSrc: function (fileName) {
      return require('@/assets/images/' + fileName)
    },
    imgSrcTumb: function (fileName) {
      return require('@/assets/images/m/' + fileName)
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
<style lang="scss" scoped>
.detail {
  &-cover {
    // background-color:#e6e6e6;
    padding-top:30px;
    width:1180px;
    height: 600px;
    overflow:hidden;
    margin:0 auto;
    position: relative;

    > img {
      padding-top:10px;
      margin-bottom:10px;
    }

    &-list {
      width:1180px;
      margin:0 auto;
      text-align: left;
      padding-top:10px;

      img {
        cursor:pointer;
        width:80px;
        border:2px solid transparent;
      }

      img+img {
        margin-left:10px;
      }

      img.active {
        border:2px solid #03a9f4;
      }
    }
  }

  &-info {
    box-sizing: border-box;
    width:1180px;
    margin:0 auto;
    text-align: left;
    position:relative;

    &-user {
      position:absolute;
      right:0px;
      top:10px;
    }
    h1 {
      font-size: 2em;
      border-bottom: 1px dashed #eaecef;
    }
  }
}

.img {
  width:200px;
  border:1px solid #e6e6e6;
}
</style>
