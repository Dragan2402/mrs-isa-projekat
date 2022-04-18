<template>   
  <div v-if="loaded" align='center'>
      <p>Name: {{rentingEntity.name}}</p>
      <p>Address: {{rentingEntity.address}}</p>
      <p>Promo Description: {{rentingEntity.promoDescription}}</p>
      <p>Behavior Rules: {{rentingEntity.behaviourRules}}</p>
      <p>Price: {{rentingEntity.priceList}}</p>
      <p>Additional Info: {{rentingEntity.additionalInfo}}</p>
      <p>Cancellation conditions: {{rentingEntity.cancellationConditions}}</p>
      <p>Pictures: //to do insert pictures//</p>
      <p v-if="this.displayType==0">Rooms Quantity: {{rentingEntity.roomsQuantity}}</p>
      <p v-if="this.displayType==0">Beds Per Room: {{rentingEntity.bedsPerRoom}}</p>
      <p v-if="this.displayType==1">Type: {{rentingEntity.type}}</p>
      <p v-if="this.displayType==1">Length: {{rentingEntity.length}}</p>
      <p v-if="this.displayType==1">Engine Number: {{rentingEntity.engineNumber}}</p>
      <p v-if="this.displayType==1">Engine Power: {{rentingEntity.enginePower}}</p>
      <p v-if="this.displayType==1">Top Speed: {{rentingEntity.topSpeed}}</p>
      <p v-if="this.displayType==1">Client Limit: {{rentingEntity.clientLimit}}</p>
      <p v-if="this.displayType==2">Instructor Biography: {{rentingEntity.instructorBiography}}</p>
      <p v-if="this.displayType==2">Client Limit: {{rentingEntity.clientLimit}}</p>      
    </div>
</template>

<script>
import axios from "axios"

export default {
  name: "RentingEntityPreview",
  data(){
    return {
      id:0,
      displayType:0,
      rentingEntity: {},
      loaded:false,
    }
  },
  mounted(){
    // axios.get("api/ships/all").then( response => this.ships=response.data);
    this.displayType = this.$route.params.displayType;
    this.id=this.$route.params.id;
    this.loaded=false;
    if(this.displayType==0 && this.id != undefined){      
      const path="/api/vacation_houses/"+this.id;
      axios.get(path).then( response => this.rentingEntity=response.data);
      this.loaded=true;
      }
    else if(this.displayType==1 && this.id != undefined){
      const path="/api/ships/"+this.id;
      axios.get(path).then( response => this.rentingEntity=response.data);
      this.loaded=true;
    }
    else if(this.id != undefined){
      const path="/api/fishingClasses/"+this.id;
      axios.get(path).then( response => this.rentingEntity=response.data);
      this.loaded=true;
    }
}}
</script>

<style scoped>

</style>