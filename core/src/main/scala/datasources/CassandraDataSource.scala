package datasources

case class CassandraDataSource(url: String,
                               user: Option[String],
                               password: Option[String],
                               keyspace: Option[String])
    extends DataSource
