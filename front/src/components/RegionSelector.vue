<template>
  <div class="main">
    <select ref="select-county" @change="actionSelectRegion" v-model="regions.county">
      <option :value="null">선택해주세요</option>
      <option v-for="region in countyList" :key="region.code" :value="region">{{ region.county }}</option>
    </select>
    <select ref="select-town" @change="actionSelectRegion" v-model="regions.town">
      <option :value="null">선택해주세요</option>
      <option v-for="region in townList" :key="region.code" :value="region">{{ region.town }}</option>
    </select>
    <select ref="select-district" @change="actionSelectRegion" v-model="regions.district">
      <option :value="null">선택해주세요</option>
      <option v-for="region in districtList" :key="region.code" :value="region">{{ region.district }}</option>
    </select>
  </div>
</template>

<script>
import regionService from '@/services/regionService'

export default {
  name: 'RegionSelector',
  data() {
    return {
      regions: {
        county: null,
        town: null,
        district: null
      },
      countyList: null, // [ regions ]
      townList: null, // [ regions ]
      districtList: null // [ regions ]
    }
  },
  methods: {
    actionSelectRegion(evt) {
      const target = evt.target
      if (target === this.$refs['select-county']) {
        this.regions.town = null
        this.townList = null
        this.regions.district = null
        this.districtList = null
        this.loadRegions()
      } else if (target === this.$refs['select-town']) {
        this.regions.district = null
        this.districtList = null
        this.loadRegions()
      }

      let region
      if (this.regions.district != null) {
        region = this.regions.district
      } else if (this.regions.town != null) {
        region = this.regions.town
      } else {
        region = this.regions.county
      }
      this.$emit('onSelectRegion', region)
    },
    loadRegions() {
      if (this.countyList == null) {
        regionService.getRegions().then(res => {
          this.countyList = res.data
        })
      }
      if (this.townList == null && this.regions.county != null) {
        const code = this.regions.county.code.substring(0, 2) + '*'
        regionService.getRegions(code).then(res => {
          this.townList = res.data
        })
      }
      if (this.districtList == null && this.regions.town != null) {
        const code = this.regions.town.code.substring(0, 4) + '*'
        regionService.getRegions(code).then(res => {
          this.districtList = res.data
        })
      }
    }
  },
  mounted() {
    this.loadRegions()
  }
}
</script>

<style lang="scss" scoped>

.main {

  select {
    margin: 10px;
    width: 150px;
    height: 40px;
    border: 1px solid lightgray;
    border-radius: 5px;
    font-size: 16px;
    padding: 10px;
  }
}
</style>
