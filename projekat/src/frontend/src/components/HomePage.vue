<template>
  <div class="main-container">
    <div class="left-bar">
      <h5 style="color: white; font-weight: bold">Search</h5>
      <div class="left-bar-text">Destination name:</div>
      <div>
        <input class="input-group-text" type="text" placeholder="Filter by name, address..." v-model="filter"/>
      </div>
      <div class="left-bar-text">Date:</div>
      <Datepicker v-model="availabilityInterval" :format="formatRange" range clearable/>
      <div class="left-bar-text">Sort by:</div>
      <div class="sort">
        <select class="form-select" v-model="sortType" name="example">
          <option value=0>Name descending</option>
          <option value=1>Name ascending</option>
          <option value=2>Price descending</option>
          <option value=3>Price ascending</option>
        </select>
      </div>
      <br>
      <div class="radio-div">
        <div>
          <input class="radio" type="radio" id="contactChoice1" name="choice" value=0 v-model="displayType">
          <label class="custom-btn button-outline" for="contactChoice1">Houses</label>
        </div>
        <div>
          <input class="radio" type="radio" id="contactChoice2" name="choice" value=1 v-model="displayType">
          <label class="custom-btn button-outline" for="contactChoice2">Boats</label>
        </div>
        <div>
          <input class="radio" type="radio" id="contactChoice3" name="choice" value=2 v-model="displayType">
          <label style="margin: 0;" class="custom-btn button-outline" for="contactChoice3">Fishing classes </label>
        </div>
      </div>
    </div>
    <div class="entities-div">
      <div v-if="this.displayType==0">
        <div class="list-entities" v-for="(vacationHouse, index) in this.filteredVacationHouses" @Click="selectEntity(vacationHouse)"
            v-bind:index="index" :key="vacationHouse.id" v-bind="{selected: selectedEntity.id===vacationHouse.id}">
          <div class="entity-picture"><img v-bind:src="'data:image/jpeg;base64,' + vacationHouse.img" @click="jumpToPreview(vacationHouse)" style="width: 200px; height: 150px; cursor: pointer"></div>
          <div class="entity-name"><h4 @click="jumpToPreview(vacationHouse)" style="font-weight: bold; cursor: pointer">{{ vacationHouse.name }}</h4>
            <div class="entity-description"><i class="bi bi-geo-alt-fill"></i> {{ vacationHouse.address }} </div>
            <div class="entity-description">{{ vacationHouse.promoDescription }}</div>
            <vue3-star-ratings class="star-ratings" v-model="vacationHouse.rating" starSize="15"  :showControl=false :disableClick=true :step=0 />
            <span style="color: #585858;">({{vacationHouse.reviewsNumber}})</span>
          </div>
          <div class="entity-price">
            <div><h5 style="font-weight: bold">{{ vacationHouse.priceList }}</h5></div>
            <div>
              <button class="custom-btn button-primary" @click="jumpToPreview(vacationHouse)">Explore</button>
            </div>
          </div>
        </div>

      </div>
      <div v-if="this.displayType==1">
        <div class="list-entities" v-for="(ship,index) in this.filteredShips" @Click="selectEntity(ship)" v-bind:index="index" :key="ship.id"
            v-bind="{selected: selectedEntity.id===ship.id}">
        <div class="entity-picture"><img v-bind:src="'data:image/jpeg;base64,' + ship.img" @click="jumpToPreview(ship)" style="width: 200px; height: 150px; cursor: pointer"></div>
          <div class="entity-name"><h4 @click="jumpToPreview(ship)" style="cursor: pointer; font-weight: bold">{{ ship.name }}</h4>
            <div class="entity-description"><i class="bi bi-geo-alt-fill"></i> {{ ship.address }} </div>
            <div class="entity-description">{{ship.promoDescription}}</div>
            <vue3-star-ratings class="star-ratings" v-model="ship.rating" starSize="15"  :showControl=false :disableClick=true :step=0 />
            <span style="color: #585858;">({{ship.reviewsNumber}})</span>
          </div>
          <div class="entity-price">
            <div><h5 style="font-weight: bold">{{ ship.priceList }}</h5></div>
            <div><button class="custom-btn button-primary" @click="jumpToPreview(ship)">Explore</button></div>
          </div>
        </div>
      </div>
      <div v-if="this.displayType==2">
        <div class="list-entities" v-for="(fishingClass,index) in this.filteredFishingClasses" @Click="selectEntity(fishingClass)"
             v-bind:index="index" :key="fishingClass.id" v-bind="{selected: selectedEntity.id===fishingClass.id}">
          <div class="entity-picture"><img v-bind:src="'data:image/jpeg;base64,' + fishingClass.img" @click="jumpToPreview(fishingClass)" style="width: 200px; height: 150px; cursor: pointer"></div>
          <div class="entity-name"><h4 @click="jumpToPreview(fishingClass)" style="cursor: pointer; font-weight: bold">{{ fishingClass.name }}</h4>
          <div class="entity-description"><i class="bi bi-geo-alt-fill"></i> {{ fishingClass.address }} </div>
          <div class="entity-description">{{fishingClass.promoDescription}}</div>
          <vue3-star-ratings class="star-ratings" v-model="fishingClass.rating" starSize="15"  :showControl=false :disableClick=true :step=0 />
            <span style="color: #585858;">({{fishingClass.reviewsNumber}})</span>
          </div>
          <div class="entity-price">
            <div><h5 style="font-weight: bold">{{ fishingClass.priceList }}</h5></div>
            <div><button class="custom-btn button-primary" @click="jumpToPreview(fishingClass)">Explore</button></div>
          </div>
        </div>
      </div>
      <br><br><br><br><br><br>
      <button class="btn btn-primary" @click="goToUserProfile()">User Profile</button>
      <button class="btn btn-primary" @click="goToFishingClassProfile()">Fishing Class Profile</button>
      <button class="btn btn-primary" @click="goToVacationHouseProfile()">Vacation House Profile</button>
      <button class="btn btn-primary" @click="goToShipProfile()">Ship Profile</button>
    </div>
  </div>
</template>

<script>
import axios from "axios"

export default {

  name: "HomePage",
  data() {
    return {
      publicPath: process.env.BASE_URL,
      fishingClasses: [],
      vacationHouses: [],
      ships: [],
      selectedEntity: {},
      selectedRows: [],
      availabilityInterval: null,
      sortType: 0, //represents type of sort, 0 name desc, 1 name asc, 2 price asc, 3 price desc
      filter: "",
      selected: false,
      displayType: 0,  //represents type of renting entity, 0 vac houses, 1 ships , 2 fishing classes
    }
  }
  ,
  mounted() {
    axios.get("api/vacation_houses/all").then(response => this.vacationHouses=response.data);
    axios.get("api/fishingClasses/all").then(response => this.fishingClasses = response.data);
    axios.get("api/ships/all").then(response => this.ships = response.data);

  },
  
  computed: {
    filteredVacationHouses() {
      return this.vacationHouses.filter(row => {
        return this.includes(row);
      }).sort((a,b)=>{
        return this.sort(a,b);})
    },
    filteredShips() {
      return this.ships.filter(row => {
        return this.includes(row);
      }).sort((a,b)=>{
       return this.sort(a,b);
      })
    },
    filteredFishingClasses() {
      return this.fishingClasses.filter(row => {
        return this.includes(row);
      }).sort((a,b)=>{
       return this.sort(a,b);
      })
    },
  },
  methods: {       

    goToUserProfile() {
      this.$router.push('/clientProfile');
    },
    goToFishingClassProfile() {
      this.$router.push('/fishingClassProfile');
    },
    formatRange(dates){
      let from = dates[0];
      let to = dates[1];
      if(from==null){
        from=new Date()
      }
      const dayFrom = from.getDate();
      const monthFrom = from.getMonth() + 1;
      const yearFrom = from.getFullYear();
      const hourFrom = from.getHours();
      const minuteFrom = from.getMinutes();

      if(to==null){
        to=new Date()
      }
      const dayTo = to.getDate();
      const monthTo = to.getMonth() + 1;
      const yearTo = to.getFullYear();
      const hourTo = to.getHours();
      const minuteTo = to.getMinutes();

      return `${dayFrom}.${monthFrom}.${yearFrom} ${hourFrom}:${minuteFrom} - ${dayTo}.${monthTo}.${yearTo} ${hourTo}:${minuteTo}`
    },
    goToVacationHouseProfile() {
      this.$router.push({
        name: "vacationHouseProfile",
        params: {id: 3}
      });
    },
    goToShipProfile() {
      this.$router.push({
        name: "shipProfile",
        params: {id: 1}
      });
    },
    selectEntity(entity) {

      this.selectedEntity = entity;
      this.selected = true;

    },
    dateFromLocal(dateString){
      if(dateString==null){
        return new Date();
      }
      
      const parts=dateString.split(" ");
      
      if( parts.length !=2){
        return new Date();
      }
      const dateParts= parts[0].split(".");
      
      if( dateParts.length !=3){
        return new Date();
      }
      const hourParts=parts[1].split(":");
      
      if( hourParts.length !=2){
        return new Date();
      }
      
      const day = dateParts[0];
      const month = dateParts[1];
      const year = dateParts[2];
      const hours = hourParts[0];
      const minutes = hourParts[1];
      // new Date(year, month, day, hours, minutes, seconds, milliseconds)
      const date= new Date(year,month-1,day,hours,minutes);
      return date;
    },
    includes(row){
        
        const name = row.name.toString().toLowerCase();
        const address = row.address.toString().toLowerCase();
        const price = row.priceList.toString().toLowerCase();
        const searchTerm = this.filter.toLowerCase();
        
        const availableFrom = this.dateFromLocal(row.availableFrom);        
        const availableTo= this.dateFromLocal(row.availableTo);
        let dateInInterval=false;
        let dateFilterOn=false;
        if(this.availabilityInterval != null){
          dateFilterOn=true;
          let fromFilter = this.availabilityInterval[0]
          let toFilter= this.availabilityInterval[1]
          if(fromFilter==null){
            fromFilter=new Date();
          }
          if(toFilter==null){
            toFilter=new Date()
          }
          if(availableFrom<=fromFilter && availableTo>=toFilter){
            dateInInterval=true;
            
          }else{
            dateInInterval=false;
            
          }
        }
        
        if(dateFilterOn && dateInInterval){
          return (name.includes(searchTerm) || address.includes(searchTerm) || price.includes(searchTerm))
        }
        else if(dateFilterOn && !dateInInterval){
          return false;
        }
        return (name.includes(searchTerm) || address.includes(searchTerm) || price.includes(searchTerm))
        
    },
    sort(a,b){
        if(this.sortType==0){
          let fa = a.name.toLowerCase(), fb = b.name.toLowerCase();
          if (fa < fb) {
            return -1
          }
          if (fa > fb) {
            return 1
          }
          return 0
        }
        else if (this.sortType == 1){
          let fa = a.name.toLowerCase(), fb = b.name.toLowerCase();
           if (fa > fb) {
            return -1
          }
          if (fa < fb) {
            return 1
          }
          return 0
        }
        if(this.sortType==2){
        let fa =parseInt(a.priceList), fb =parseInt(b.priceList)
        
          if (fa > fb) {
            return -1
          }
          if (fa < fb) {
            return 1
          }
          return 0
        }
        else if (this.sortType == 3){
          
          let fa = parseInt(a.priceList), fb =parseInt(b.priceList);
          
           if (fa < fb) {
            return -1
          }
          if (fa > fb) {
            return 1
          }
          return 0
        }

    },
    jumpToPreview(entity) {
      this.selectEntity(entity);
      this.$router.push({
        name: "rentingEntityPreview",
        params: {id: this.selectedEntity.id, displayType: this.displayType}
      });
    }

  }
}
</script>


<style scoped>

.input-group-text {
  width: 100%;
  margin: auto;
}

.list-entities {
  text-align: center;
  width: 100%;
  height: 170px;
  border: 1px solid darkgrey;
  border-radius: 5px;
  overflow: hidden;
  margin-bottom: 10px;
}

.entities-div {
  margin-left: 26%;
}

.main-container {
  min-width: 1000px;
  width: 60%;
  margin: 20px auto auto;
}

.left-bar {
  margin-top: 0;
  padding: 10px;
  border-radius: 5px;
  float: left;
  width: 24%;
  background-color: #00587a;
}

.left-bar-text{
  margin-top: 10px;
  color: white;
}

.datepicker-sort select {
  margin-left: 30px;
  width: 200px;
}

div.star-ratings {
  padding: 0;
  margin: 0;
  float: left;
}

.entity-picture img {
  object-fit: cover;
  margin: 10px;
  border-radius: 5px;
  width: 200px;
  height: 150px;
}

.entity-picture {
  float:left;
}

.entity-name {
  float: left;
  margin: 10px;
  text-align: left;
}

.entity-description {
  color: #585858;
  max-width: 280px;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.entity-price {
  float: right;
  margin: 50px 30px 0 0;
  min-width: 150px;
}

.radio {
  opacity: 0;
  width: 0;
  height: 0;
}

.button-outline {
  cursor: pointer;
  width: 100%;
  margin-bottom: 25px;
}

[type=radio]:checked + label {
  background-color: #004661;
}

</style>