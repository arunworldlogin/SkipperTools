# SkipperTools
Project used to ease the android development by skipping/reducing commenly used boilerplate code

# Tool 1 : Generic RecyclerView Adapter

        //Dummy data
        val data = arrayListOf(TestData("One"), TestData("Two"), TestData("Three"))
        val emptyStateData = TestData("Empty List")

        //View holder binder for Recycler View list item
        val vieHolderBinder = object : GenericRecyclerViewAdapter.ViewBinder {
            override fun bind(data: Any, binding: ViewDataBinding) {
                (binding as ListItemLayoutBinding).testData = data as TestData
            }
        }

        //View holder binder for empty state
        val emptyStateViewHolderBinder = object : GenericRecyclerViewAdapter.ViewBinder {
            override fun bind(data: Any, binding: ViewDataBinding) {
                (binding as EmptyStateLayoutBinding).testData = data as TestData
            }
        }

        //Setting Recyclerview adapter
        val recycleViewAdapter =
            GenericRecyclerViewAdapter(
                 this,
                 R.layout.list_item_layout,
                 vieHolderBinder,
                 data,
                 R.layout.empty_state_layout,
                 emptyStateViewHolderBinder,
                 emptyStateData, object : GenericRecyclerViewAdapter.OnClickListener<TestData>{
                     override fun onClick(position: Int, data: TestData) {
                         Toast.makeText(this@MainActivity, "Clicked item at $position", Toast.LENGTH_LONG).show()
                     }

                 }
            )
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.adapter = recycleViewAdapter

# Tool 1 : Retrofit instance

        Retrofit instance with moshi converter and okhhtp logging intercepter

        //Getting retrofit instance
        RetrofitClient.getInstance("YOUR_BASE_URL").apply {
            //create(YourService::class.java)
        }


Adding from gradle:

        #Add it in your root build.gradle at the end of repositories:

        allprojects {
            repositories {
                ...
                maven { url 'https://jitpack.io' }
            }
        }

        #Add the dependency

        dependencies {
        	        implementation 'com.github.arunworldlogin:SkipperTools:VERSION'
        }

You can find the latest version here : https://github.com/arunworldlogin/SkipperTools/releases
