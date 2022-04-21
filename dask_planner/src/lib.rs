use mimalloc::MiMalloc;
use pyo3::prelude::*;

mod expression;
mod sql;

#[global_allocator]
static GLOBAL: MiMalloc = MiMalloc;

/// Low-level DataFusion internal package.
///
/// The higher-level public API is defined in pure python files under the
/// dask_planner directory.
#[pymodule]
#[pyo3(name = "rust")]
fn rust(_py: Python, m: &PyModule) -> PyResult<()> {
    // Register the python classes
    m.add_class::<expression::PyExpr>()?;
    m.add_class::<sql::DaskSQLContext>()?;
    m.add_class::<sql::types::DaskRelDataType>()?;
    m.add_class::<sql::statement::PyStatement>()?;
    m.add_class::<sql::schema::DaskSchema>()?;
    m.add_class::<sql::table::DaskTable>()?;
    m.add_class::<sql::function::DaskFunction>()?;
    m.add_class::<sql::table::DaskStatistics>()?;
    m.add_class::<sql::logical::PyLogicalPlan>()?;

    Ok(())
}
