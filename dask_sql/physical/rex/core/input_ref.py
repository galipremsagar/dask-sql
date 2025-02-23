from typing import TYPE_CHECKING

import dask.dataframe as dd

from dask_sql.datacontainer import DataContainer
from dask_sql.physical.rex.base import BaseRexPlugin

if TYPE_CHECKING:
    import dask_sql
    from dask_sql.java import org


class RexInputRefPlugin(BaseRexPlugin):
    """
    A RexInputRef is an expression, which references a single column.
    It is typically to be found in any expressions which
    calculate a function in a column of a table.
    """

    class_name = "org.apache.calcite.rex.RexInputRef"

    def convert(
        self,
        rex: "org.apache.calcite.rex.RexNode",
        dc: DataContainer,
        context: "dask_sql.Context",
    ) -> dd.Series:
        df = dc.df
        cc = dc.column_container

        # The column is references by index
        index = rex.getIndex()
        backend_column_name = cc.get_backend_by_frontend_index(index)
        return df[backend_column_name]
