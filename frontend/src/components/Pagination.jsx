import styles from "@/styles/components/Pagination.module.css";

export default function Pagination({
  totalItems,
  itemsPerPage = 10,
  currentPage,
  onPageChange,
}) {

  const totalPages = Math.ceil(totalItems / itemsPerPage);
  if (totalPages <= 1) return null;

  const pages = Array.from({ length: totalPages }, (_, i) => i + 1);

  return (
    <nav className={styles.pagination}>
      <ul className={styles.pageList}>
        {pages.map((page) => (
          <li key={page} className={styles.pageItem}>
            <button
              className={`${styles.pageButton} ${
                page === currentPage ? styles.active : ""
              }`}
              onClick={() => onPageChange(page)}
            >
              {page}
            </button>
          </li>
        ))}
      </ul>
    </nav>
  );
}