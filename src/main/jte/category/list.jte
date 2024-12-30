@import cz.trkan.pproprojekt.model.Category
@import cz.trkan.pproprojekt.utils.JteUtils
@param java.util.List<Category> categories

@template.layout(
content = @`
    <div class="container">
        <h2 class="mb-4">All Categories</h2>

        @if (JteUtils.isEditor())
            <div class="mb-3">
                <a href="/categories/create" class="btn btn-primary">Create New Category</a>
            </div>
        @endif

        @if(categories.isEmpty())
            <div class="alert alert-info" role="alert">
                No categories found. Create a new category to get started.
            </div>
        @else
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-light">
                    <tr>
                        @if (JteUtils.isEditor())
                            <th>ID</th>
                        @endif
                        <th>Name</th>
                        @if (JteUtils.isEditor())
                            <th>Actions</th>
                        @endif
                    </tr>
                    </thead>
                    <tbody>
                    @for(Category category : categories)
                        <tr>
                            @if (JteUtils.isEditor())
                                <td>${category.getId()}</td>
                            @endif
                            <td>${category.getName()}</td>
                            @if (JteUtils.isEditor())
                                <td>
                                    <a href="/categories/edit/${category.getId()}" class="btn btn-sm btn-outline-primary">Edit</a>
                                    <a href="/categories/delete/${category.getId()}" class="btn btn-sm btn-outline-danger"
                                        onclick="return confirm('Are you sure you want to delete this category?')">Delete</a>
                                </td>
                            @endif
                        </tr>
                    @endfor
                    </tbody>
                </table>
            </div>
        @endif
    </div>
`
)
