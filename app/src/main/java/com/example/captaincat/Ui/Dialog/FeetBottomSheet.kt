
import android.app.Dialog
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.captaincat.api.ReadyOrPetModel
import com.example.captaincat.api.UserBagModel
import com.example.captaincat.api.UserLevelsModel
import com.example.captaincat.model.Thing
import com.example.captaincat.MyApplication.Companion.apiService
import com.example.captaincat.MyApplication.Companion.appContext
import com.example.captaincat.MyGame
import com.example.captaincat.MyGame.Companion.pet
import com.example.captaincat.Ui.Adapter.ObjAdapter
import com.example.captaincat.databinding.BottomFeedBinding
import com.example.lamstest.Common.toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Response

class FeetBottomSheet( ) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomFeedBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = BottomFeedBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(binding.root)

        apiService.getBag(MyGame.user.id.toString() + "")
            .enqueue(object : retrofit2.Callback<List<Thing>>{
                override fun onResponse(
                    call: Call<List<Thing>>,
                    response: Response<List<Thing>>
                ) {
                    val list = response.body()?: mutableListOf()
                    UserLevelsModel.bag = list as MutableList<Thing>
                    binding.rvObj.adapter = ObjAdapter(appContext,list.filter { it.type==1 },true)
                    {
                        if(pet.mood>=100&&pet.energy>=100){
                            "我已经吃不下啦！".toast()
                            return@ObjAdapter
                        }
                        UserBagModel.userSpendThings(listOf(it.apply {
                            this.num = 1
                        }))
                        ReadyOrPetModel.feedPet(pId = pet.id,10,it.price/15)

                        dismiss()
                    }
                    binding.rvObj.layoutManager =
                        StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
                }
                override fun onFailure(call: Call<List<Thing>>, t: Throwable) {
                    "连接错误:${t.message}"
                }

            })


        return bottomSheetDialog
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}
