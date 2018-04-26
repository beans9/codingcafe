<template>
  <div class="hello">
    {{title}}
    <p>카페명: <input type="text" v-model="cafe.name"/></p>
    <P>설명: <textarea v-model="cafe.memo"></textarea></P>
    <div>사진
      <br/>

      <div v-for="(data,index) in files" :key="index">
        {{data.name}}
      </div>
      <div v-for="(file,index) in this.photos" v-bind:key="index" >
        <input type="file" id="files" ref="files" />
        <input type="radio" :value="index" v-model="defultImgIdx" />
        <input type="button" value="삭제" @click="delFile(file)" />
        <br/>
      </div>
      <input type="file" id="files" ref="files" @change="handleFilesUpload" />
      <input type="button" value="파일추가" @click="addFile()"/>
    </div>
    <input type="button" @click="proc()" value="저장"/>
  </div>
</template>

<script>
// import _ from 'lodash'
import {cafes} from '@/api/api'
export default {
  name: 'CafeWrite',
  data () {
    return {
      title: '입력',
      cafe: {
        name: '',
        memo: ''
      },
      files: [],
      photos: [],
      photosIdx: 0,
      defultImgIdx: 0
    }
  },
  created: function () {
    // this.addFile()
  },
  methods: {
    proc: function () {
      if (this.$refs.files[this.defultImgIdx].files === null) {
        alert('null')
        return
      }
      cafes.insert(this.cafe, this.$refs.files, this.defultImgIdx).then((res) => {
        setTimeout(() => {
          this.$router.push('/cafe/' + res.id)
        }, 1000)
      })
    },
    addFile: function () {
      this.photos.push({idx: this.photosIdx++})
    },
    delFile: function (photo) {
      if (this.photos.length === 1) {
        return
      }
      let photoIdx = this.photos.indexOf(photo)
      this.photos.splice(photoIdx, 1)
      if (photoIdx === this.defultImgIdx) {
        this.defultImgIdx = 0
      }
    },
    handleFilesUpload: function (e) {
      this.files.push(files[0])
      console.log(this.files)
      this.$refs.files = null
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
