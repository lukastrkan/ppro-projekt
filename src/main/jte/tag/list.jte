@import cz.trkan.pproprojekt.model.Tag
@import cz.trkan.pproprojekt.utils.JteUtils
@param java.util.List<Tag> tags

@template.layout(
content = @`
    <div class="container">
        <h2 class="mb-4">All Tags</h2>

        @if(JteUtils.isEditor())
            <div class="mb-3">
                <a href="/tags/create" class="btn btn-primary">Create New Tag</a>
            </div>
        @endif

        @if(tags.isEmpty())
            <div class="alert alert-info" role="alert">
                No tags found. Create a new tag to get started.
            </div>
        @else
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-light">
                    <tr>
                        @if(JteUtils.isEditor())
                            <th>ID</th>
                        @endif
                        <th>Name</th>
                        @if(JteUtils.isEditor())
                            <th>Actions</th>
                        @endif
                    </tr>
                    </thead>
                    <tbody>
                    @for(Tag tag : tags)
                        <tr>
                            @if(JteUtils.isEditor())
                                <td>${tag.getId()}</td>
                            @endif
                            <td>${tag.getName()}</td>
                            @if(JteUtils.isEditor())
                                <td>
                                    <a href="/tags/edit/${tag.getId()}" class="btn btn-sm btn-outline-primary">Edit</a>
                                    <a href="/tags/delete/${tag.getId()}" class="btn btn-sm btn-outline-danger"
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
