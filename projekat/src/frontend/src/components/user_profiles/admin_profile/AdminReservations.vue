<template>
  <div style="display: flex; justify-content: center">
  <div class="main-wrapper">
      <div style="display: flex; align-content: space-between; margin: 15px 0 15px 0">
        <Datepicker v-model="range" :format="formatRange" range clearable/>
          <div style="width: 70%">
            <input class="input-group-text" type="text" placeholder="Filter by name" v-model="filterR"/>
          </div>
          <br>
          <div style="width: 30%">
            <div style="width: 95%; float: right">
              <select class="form-select" v-model="sortTypeR" name="example">
                <option value=0>Place descending</option>
                <option value=1>Place ascending</option>
              </select>
            </div>
          </div>
        </div>
    <div class="for-container" v-for="(reservation, index) in this.filteredReservations" v-bind:index="index" :key="reservation.id">
      <div class="inner-container">
        <div class="left-container">
          <div><b>Entity:</b> {{ reservation.rentingEntityName }}</div>
          <div><b>Profit:</b> {{ (reservation.price * reservation.fee).toFixed(2) }} &#8364; </div>
          <div><b>Date:</b> {{ reservation.start }}</div>
        </div>
      </div>
    </div>
  </div>
    <div class="inner-container" style="text-align: left; display: block; margin-top: 15px; margin-left: 15px; width: 200px; height: 200px">
      <div><b>Total profit:</b> {{ totalProfit.toFixed(2) }} &#8364;</div>
      <br>
      <div><b>Reservations count:</b> {{ reservations.length }}</div>
    </div>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AdminReservations",
  data() {
    return {
      reservations: [],
      totalProfit: 0,
      sortTypeR:0,
      filterR: "",
      range: [],
    }
  },
  mounted() {
    axios.get("api/admins/reservations/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
      this.reservations = response.data;
      console.log(this.reservations);
      for(const res of this.reservations) {
        this.totalProfit += res.price * res.fee;
      }
    })
  },
  computed:{
     filteredReservations(){
       return this.reservations.filter(row => {
        return this.includesH(row);
      }).sort((a,b)=>{
        return this.sort(a,b,this.sortTypeR);})
    }},
  methods: {
    includesH(row){

        
        const name = row.place.toString().toLowerCase();
        const rentingEntityName = row.rentingEntityName.toString().toLowerCase();
        const searchTerm = this.filterR.toLowerCase();
        const dateStartt = this.dateFromLocal(row.start);
        const dateEnd = this.dateFromLocal(row.start);

        dateEnd.setMilliseconds(row.duration);
        

        let enteredDate=false;
        if(this.range !=null){
        if(this.range.length==2){
          if(this.range[1] != null){
            enteredDate=true; 
          }
        }}
        if(!enteredDate){
        return (name.includes(searchTerm) || rentingEntityName.includes(searchTerm))
        }else{
          console.log(dateEnd);
          if(dateStartt > this.range[0] && dateEnd <= this.range[1]){
            return (name.includes(searchTerm) || rentingEntityName.includes(searchTerm) )
          }
        }
        
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
    sort(a,b,sortType){
        if(sortType==0){
          let fa = a.place.toLowerCase(), fb = b.place.toLowerCase();
          if (fa < fb) {
            return -1
          }
          if (fa > fb) {
            return 1
          }
          return 0
        }
        else if (sortType== 1){
          let fa = a.place.toLowerCase(), fb = b.place.toLowerCase();
           if (fa > fb) {
            return -1
          }
          if (fa < fb) {
            return 1
          }
          return 0
        }
        if(sortType==2){
        let fa =parseInt(a.price), fb =parseInt(b.price)
        
          if (fa > fb) {
            return -1
          }
          if (fa < fb) {
            return 1
          }
          return 0
        }
        else if (sortType == 3){
          
          let fa = parseInt(a.price), fb =parseInt(b.price);
          
           if (fa < fb) {
            return -1
          }
          if (fa > fb) {
            return 1
          }
          return 0
        }

    },

  }
}
</script>

<style scoped>

.main-wrapper {
  margin-bottom: 20%;
}

.for-container {
  display: flex;
  justify-content: center;
  margin-top: 15px
}

.inner-container {
  border: 1px solid #00587a;
  display: flex;
  padding: 10px;
  border-radius: 5px
}

.left-container {
  text-align: left;
  width: 30rem;
}

</style>