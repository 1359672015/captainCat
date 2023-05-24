package com.example.captaincat.Ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.captaincat.model.Pet
import com.example.captaincat.R

class PetAdapter(
    private val pets: List<Pet>,
    var onClick :(Pet)->Unit ={},
) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {


    inner class PetViewHolder(itemView: View, onClick :(Pet)->Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val imageViewPet: ImageView = itemView.findViewById(R.id.imageViewPet)
        private val textViewPetType: TextView = itemView.findViewById(R.id.textViewPetType)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onClick.invoke(pets[position] )
                }
            }
        }

        fun bind(pet: Pet) {
            imageViewPet.setImageResource(getPetImageResId(pet.look))
            textViewPetType.text = pet.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pet, parent, false)
        return PetViewHolder(view, onClick)
    }

    override fun getItemCount(): Int {
        return pets.size
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(pets[position])
    }

    private fun getPetImageResId(petLook: Int): Int {
        val imageIds = arrayListOf(
            R.drawable.elf_orange,
            R.drawable.elf_blue,
            R.drawable.elf_tomato,
            R.drawable.elf_peach,
            R.drawable.elf_apple
        )
        return imageIds[petLook]
    }

}
