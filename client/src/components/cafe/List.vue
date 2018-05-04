<template>
  <div class="list">
    <ul class="cafe-list">
      <li v-for="item in list" v-bind:key="item.id">
        <a class="cafe-list-anchor" @click="detail(item)">
            <span class="cafe-list-anchor-title">{{item.name}}</span>
            <template v-if="item.photos.length > 0">
              <img :src="imgSrc(item.photos[0].name)" class="cafe-list-anchor-cover"/>
            </template>
        </a>
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
      return require('@/assets/images/tumb/' + fileName)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
@import "~styles/_mixin.scss";

.list {
  /* border:1px solid red; */
  padding-top:30px;
  width:1200px;
  min-width:330px;
  @include respond-to('large') {
    width:95%;
  }

  margin:0 auto;
  // border:1px solid red;
  text-align: left;
}

.cafe-list li{
  list-style: none;
  float:left;
  width:25%;
  padding-top:25%;
  position:relative;
  font-size:18px;

  @include respond-to('medium') {
    width:33%;
  }

  @include respond-to('small') {
    width:50%;
    padding-top:50%;
  }

  @include respond-to('xmall') {
    width:100%;
    padding-top:50%;
  }
  // @include respond-to('large') {
  //   width:300px;
  //   height:300px;
  //   margin-left:45px;
  //   margin-top:45px;
  // }
  // @include respond-to('medium') {
  //   width:275px;
  //   height:275px;
  //   margin-left:35px;
  //   margin-top:35px;
  // }
  // @include respond-to('small') {
  //   width:200px;
  //   height:200px;
  //   margin-left:35px;
  //   margin-top:35px;
  // }
}

.cafe-list-anchor {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: 0.4rem;
  @include respond-to('xmall') {
    margin: 0.2rem;
  }

  overflow: hidden;
  padding: 1rem;
  font-size: 2rem;
  opacity: 0.6;
  cursor: pointer;
  border:1px solid #999;
  border-radius: 0px;
  font-weight: bold;
  transition: all .3s;

  &-cover {
    position: absolute;
    left: 0;
    top: 0;
    z-index: 10;
    font-size:1rem;
  }

  &-title {
    position: relative;
    font-size:1.5rem;
    @include respond-to('small') {
      font-size:1.2rem;
    }
    display:inline-block;
    top: 50%;
    width: 100%;
    transform: translateY(-50%);
    z-index: 30;
    text-align: center;
    color:white;
    text-shadow: 3px 1px 6px #000;
    background-color: rgba(255, 255, 255, 0.5);
  }
}

.cafe-list-anchor:hover {
    opacity: 1;
    font-size:1.8rem;
    // font-size: 2rem;
    transform: scale(1.02);
    // margin: -1px;
    // padding: 2rem;
    z-index: 20;
    // border-width: 0.5rem;
    // border-radius: 0.5rem;
    // box-shadow: 0 0 2rem 1rem rgba(0,0,0,0.5);
}

.img {
  width:100%;
}
</style>
