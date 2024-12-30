@import cz.trkan.pproprojekt.model.Tag
@param java.util.List<Tag> tags

@template.layout(
content = @`
    <div class="container">
        <h2 class="mb-4">All Tags</h2>

        <div class="mb-3">
            <a href="/tags/create" class="btn btn-primary">Create New Tag</a>
        </div>

        @if(tags.isEmpty())
            <div class="alert alert-info" role="alert">
                No tags found. Create a new tag to get started.
            </div>
        @else
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    @for(Tag tag : tags)
                        <tr>
                            <td>${tag.getId()}</td>
                            <td>${tag.getName()}</td>
                            <td>
                                <a href="/tags/edit/${tag.getId()}" class="btn btn-sm btn-outline-primary">Edit</a>
                                <a href="/tags/delete/${tag.getId()}" class="btn btn-sm btn-outline-danger"
                                   onclick="return confirm('Are you sure you want to delete this tag?')">Delete</a>
                            </td>
                        </tr>
                    @endfor
                    </tbody>
                </table>
            </div>
        @endif
    </div>
`
)
